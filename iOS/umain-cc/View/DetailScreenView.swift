//
//  DetailScreenView.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import SwiftUI

struct DetailScreenView: View {
    @StateObject private var viewModel: DetailScreenViewModel
    @Environment(\.dismiss) var dismiss

    init(restaurant: Restaurant, filters: [Filter]) {
        let detailViewModel = DetailScreenViewModel(restaurant: restaurant, filters: filters)
        _viewModel = StateObject(wrappedValue: detailViewModel)
    }

    var body: some View {
        NavigationStack {
            VStack {
                AsyncImage(url: URL(string: viewModel.restaurant.imageUrl)) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                } placeholder: {
                    Color.gray
                }
                .clipped()
                .frame(height: 220)
                DetailCardView(restaurant: viewModel.restaurant, filters: viewModel.filters, isOpen: viewModel.isRestaurantOpen)
                    .padding(.horizontal, 16)
                    .padding(.top, -45)
                Spacer()
            }
            .background(Color.background)
            .ignoresSafeArea()
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button {
                        dismiss()
                    } label: {
                        Image(systemName: "chevron.down")
                            .foregroundColor(.black)
                    }
                }
            }
        }
    }
}

struct DetailScreenView_Previews: PreviewProvider {
    static var previews: some View {
        DetailScreenView(restaurant: Restaurant(id: "7450001", name: "Wayne \"Chad Broski\" Burgers", rating: 4.6, filterIds: [], imageUrl: "https://food-delivery.umain.io/images/restaurant/burgers.png", deliveryTimeMinutes: 9), filters: [])
    }
}
