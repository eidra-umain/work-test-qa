//
//  HomeScreenViewModel.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

@MainActor
class HomeScreenViewModel: ObservableObject {
    @Published private var allRestaurants: [Restaurant] = []
    @Published var allFilters: [Filter] = []
    @Published var selectedFilters: [Filter] = []
    @Published var isLoadingRestaurants = true
    @Published var isLoadingFilters = false
    @Published var selectedRestaurant: Restaurant?

    var filteredRestaurants: [Restaurant] {
        guard !selectedFilters.isEmpty else {
            return allRestaurants
        }
        let selectedFilterIds = selectedFilters.map(\.id)
        return allRestaurants.filter { restaurant in
            selectedFilterIds.allSatisfy(restaurant.filterIds.contains)
        }
    }

    private let networkManager: NetworkServiceProtocol

    init(networkManager: NetworkServiceProtocol = NetworkManager.shared) {
        self.networkManager = networkManager
        fetchAllRestaurants()
    }

    func toggleFilter(_ filter: Filter) {
        if let index = selectedFilters.firstIndex(where: { $0.id == filter.id }) {
            selectedFilters.remove(at: index)
        } else {
            selectedFilters.append(filter)
        }
    }

    func setSelectedRestaurant(_ restaurant: Restaurant) {
        selectedRestaurant = restaurant
    }
}

// MARK: Initializing the values from API

extension HomeScreenViewModel {
    private func fetchAllRestaurants() {
        isLoadingRestaurants = true
        Task {
            if let restaurants = try? await networkManager.listRestaurant().get() {
                allRestaurants = restaurants.restaurants
                let filterIds = Set(restaurants.restaurants.flatMap(\.filterIds))
                fetchAllFilters(filterIds)
            }
            isLoadingRestaurants = false
        }
    }

    private func fetchAllFilters(_ filterIds: Set<String>) {
        isLoadingFilters = true
        Task {
            allFilters = await withTaskGroup(of: Result<Filter, GetFilterError>.self) { group in
                for filterId in filterIds {
                    group.addTask { [weak self] in
                        guard let self else {
                            return .failure(.unknown)
                        }
                        return await networkManager.getFilterById(id: filterId)
                    }
                }

                var results: [Filter] = []

                for await result in group {
                    if let filter = try? result.get() {
                        results.append(filter)
                    }
                }

                return results
            }
            isLoadingFilters = false
        }
    }
}
