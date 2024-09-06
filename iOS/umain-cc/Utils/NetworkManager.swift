//
//  NetworkManager.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

enum GetFilterError: Error {
    case notFound
    case unknown
}

enum ListRestaurantError: Error {
    case unknown
}

enum GetOpenStatusError: Error {
    case notFound
    case unknown
}

protocol NetworkServiceProtocol {
    func getFilterById(id: String) async -> Result<Filter, GetFilterError>
    func listRestaurant() async -> Result<RestaurantsResponse, ListRestaurantError>
    func getOpenStatusById(id: String) async -> Result<OpenStatus, GetOpenStatusError>
}

// MARK: Implementation of the singleton Network Manager's internals

class NetworkManager: NetworkServiceProtocol {
    static var shared = NetworkManager()

    private init() {}

    private enum Endpoints {
        case getFilterById(id: String)
        case listRestaurant
        case getOpenStatusById(id: String)

        var url: URL {
            let baseURL = URL(string: "https://food-delivery.umain.io/api/v1")!
            switch self {
            case let .getFilterById(id):
                return baseURL.appending(path: "/filter/\(id)")
            case .listRestaurant:
                return baseURL.appending(path: "/restaurants")
            case let .getOpenStatusById(id):
                return baseURL.appending(path: "/open/\(id)")
            }
        }
    }

    private let decoder = JSONDecoder()

    private enum HttpMethod {
        case get([URLQueryItem])
        case post(Data?)

        var name: String {
            switch self {
            case .get:
                return "GET"
            case .post:
                return "POST"
            }
        }
    }

    private enum NetworkManagerInternalError: Error {
        case badURL
        case invalidResponse
        case failedToLoadDataForRequest
        case decodingError
        case unknownErrorStatus
    }

    private struct Resource {
        let url: URL
        var method: HttpMethod = .get([])
    }

    private func loadResource(_ resource: Resource) async -> Result<(Data, HTTPURLResponse), NetworkManagerInternalError> {
        var request = URLRequest(url: resource.url)
        switch resource.method {
        case let .post(data):
            request.httpMethod = resource.method.name
            request.httpBody = data
        case let .get(queryItems):
            var components = URLComponents(url: resource.url, resolvingAgainstBaseURL: false)
            components?.queryItems = queryItems
            guard let url = components?.url else {
                ErrorManager.shared.logError(NetworkManagerInternalError.badURL)
                return .failure(.badURL)
            }
            request = URLRequest(url: url)
        }
        let configuration = URLSessionConfiguration.default
        configuration.httpAdditionalHeaders = ["Content-Type": "application/json"]
        let session = URLSession(configuration: configuration)
        do {
            let (data, response) = try await session.data(for: request)
            guard let httpResponse = response as? HTTPURLResponse else {
                ErrorManager.shared.logError(NetworkManagerInternalError.invalidResponse)
                return .failure(.invalidResponse)
            }
            return .success((data, httpResponse))
        } catch {
            ErrorManager.shared.logError(error)
            return .failure(.failedToLoadDataForRequest)
        }
    }
}

// MARK: Implementation of the NetworkServiceProtocol for NetworkManager

extension NetworkManager {
    func getFilterById(id: String) async -> Result<Filter, GetFilterError> {
        guard let (data, httpResponse) = try? await loadResource(Resource(url: Endpoints.getFilterById(id: id).url)).get() else {
            return .failure(.unknown)
        }
        guard httpResponse.statusCode == 200 else {
            if httpResponse.statusCode == 404 {
                return .failure(.notFound)
            }
            ErrorManager.shared.logError(NetworkManagerInternalError.unknownErrorStatus)
            return .failure(.unknown)
        }
        guard let filter = try? decoder.decode(Filter.self, from: data) else {
            ErrorManager.shared.logError(NetworkManagerInternalError.decodingError)
            return .failure(.unknown)
        }
        return .success(filter)
    }

    func listRestaurant() async -> Result<RestaurantsResponse, ListRestaurantError> {
        guard let (data, httpResponse) = try? await loadResource(Resource(url: Endpoints.listRestaurant.url)).get() else {
            return .failure(.unknown)
        }
        guard httpResponse.statusCode == 200 else {
            ErrorManager.shared.logError(NetworkManagerInternalError.unknownErrorStatus)
            return .failure(.unknown)
        }
        guard let restaurants = try? decoder.decode(RestaurantsResponse.self, from: data) else {
            ErrorManager.shared.logError(NetworkManagerInternalError.decodingError)
            return .failure(.unknown)
        }
        return .success(restaurants)
    }

    func getOpenStatusById(id: String) async -> Result<OpenStatus, GetOpenStatusError> {
        guard let (data, httpResponse) = try? await loadResource(Resource(url: Endpoints.getOpenStatusById(id: id).url)).get() else {
            return .failure(.unknown)
        }
        guard httpResponse.statusCode == 200 else {
            if httpResponse.statusCode == 404 {
                return .failure(.notFound)
            }
            ErrorManager.shared.logError(NetworkManagerInternalError.unknownErrorStatus)
            return .failure(.unknown)
        }
        guard let openStatus = try? decoder.decode(OpenStatus.self, from: data) else {
            ErrorManager.shared.logError(NetworkManagerInternalError.decodingError)
            return .failure(.unknown)
        }
        return .success(openStatus)
    }
}
