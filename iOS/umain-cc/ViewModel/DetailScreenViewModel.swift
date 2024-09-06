//
//  DetailScreenViewModel.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

@MainActor
class DetailScreenViewModel: ObservableObject {
    @Published var isRestaurantOpen: Bool?
    @Published var isLoading = true
    let restaurant: Restaurant
    let filters: [Filter]
    private let networkManager: NetworkServiceProtocol

    init(restaurant: Restaurant, filters: [Filter], networkManager: NetworkServiceProtocol = NetworkManager.shared) {
        self.restaurant = restaurant
        self.filters = filters
        self.networkManager = networkManager
        fetchRestaurantOpenStatus()
    }

    private func fetchRestaurantOpenStatus() {
        isLoading = true
        Task {
            if let status = try? await networkManager.getOpenStatusById(id: restaurant.id).get() {
                isRestaurantOpen = status.isCurrentlyOpen
            }
            isLoading = false
        }
    }
}
