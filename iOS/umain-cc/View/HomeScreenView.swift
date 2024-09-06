//
//  HomeScreenView.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import SwiftUI

struct HomeScreenView: View {
    @StateObject private var viewModel = HomeScreenViewModel()

    var body: some View {
        VStack {
            if !viewModel.isLoadingFilters {
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: 16) {
                        ForEach(viewModel.allFilters, id: \.id) { filter in
                            FilterView(selected: viewModel.selectedFilters.contains { $0.id == filter.id }, filter: filter)
                                .onTapGesture {
                                    viewModel.toggleFilter(filter)
                                }
                        }
                    }
                    .frame(height: 48)
                    .padding(.horizontal, 20)
                    .padding(.vertical, 22)
                }
            }
            ScrollView(.vertical, showsIndicators: false) {
                LazyVStack(spacing: 16) {
                    ForEach(viewModel.filteredRestaurants, id: \.id) { restaurant in
                        RestaurantCardView(restaurant: restaurant, filters: viewModel.allFilters.filter { restaurant.filterIds.contains($0.id) })
                            .onTapGesture {
                                viewModel.setSelectedRestaurant(restaurant)
                            }
                    }
                }
                .padding(.horizontal, 16)
            }
        }
        .background(Color.background)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Image(uiImage: UIImage(named: "ULogo")!)
                    .resizable()
                    .frame(width: 54, height: 54)
            }
        }
        .fullScreenCover(item: $viewModel.selectedRestaurant) { restaurant in
            DetailScreenView(restaurant: restaurant, filters: viewModel.allFilters.filter { restaurant.filterIds.contains($0.id) })
        }
    }
}

struct HomeScreenView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            HomeScreenView()
        }
    }
}
