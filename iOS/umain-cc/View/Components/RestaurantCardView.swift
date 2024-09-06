//
//  RestaurantCardView.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-18.
//

import SwiftUI

struct RestaurantCardView: View {
    let restaurant: Restaurant
    let filters: [Filter]

    var filterNames: String {
        filters.map(\.name).joined(separator: " • ")
    }

    var formattedTimeInterval: String {
        let formatter = DateComponentsFormatter()
        formatter.allowedUnits = [.hour, .minute]
        formatter.unitsStyle = .short
        return formatter.string(from: TimeInterval(restaurant.deliveryTimeMinutes * 60)) ?? "0 minutes"
    }

    var body: some View {
        VStack(spacing: 0) {
            AsyncImage(url: URL(string: restaurant.imageUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Color.gray
            }
            .frame(height: 132)
            .clipped()
            VStack {
                HStack(alignment: .top) {
                    VStack(alignment: .leading, spacing: 0) {
                        Text(restaurant.name)
                            .font(.uTitle1)
                            .foregroundColor(.darkText)
                        if filterNames != "" {
                            Text(filterNames)
                                .font(.uSubtitle1)
                                .foregroundColor(.subtitle)
                        }
                        HStack(spacing: 2) {
                            Image(systemName: "clock")
                                .resizable()
                                .frame(width: 12, height: 12)
                                .foregroundColor(.red)
                            Text(formattedTimeInterval)
                                .font(.uFooter1)
                                .foregroundColor(.subtitle)
                        }
                        .padding(.top, 3)
                    }
                    Spacer()
                    VStack {
                        HStack(alignment: .center, spacing: 2) {
                            Image(systemName: "star.fill")
                                .resizable()
                                .frame(width: 12, height: 12)
                                .foregroundColor(.yellow)
                            Text("\(String(restaurant.rating))")
                                .font(.uFooter1)
                                .foregroundColor(.darkText)
                        }
                    }
                }
                .frame(maxWidth: .infinity, minHeight: 12)
            }
            .frame(maxWidth: .infinity)
            .padding(8)
        }
        .frame(maxWidth: .infinity)
        .background(Color.background)
        .clipShape(RoundedCorner(radius: 12, corners: [.topLeft, .topRight]))
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 4)
    }
}

struct RestaurantCardView_Previews: PreviewProvider {
    static let mockRestaurant = Restaurant(id: "1", name: "Title", rating: 4.5, filterIds: [], imageUrl: "https://picsum.photos/360/240", deliveryTimeMinutes: 12)

    static let mockFilters: [Filter] = [.init(id: "1", name: "Takeout", imageUrl: ""), .init(id: "2", name: "Eat-In", imageUrl: "")]

    static var previews: some View {
        RestaurantCardView(restaurant: mockRestaurant, filters: mockFilters)
            .previewDisplayName("With filters")

        RestaurantCardView(restaurant: mockRestaurant, filters: [])
            .previewDisplayName("Without filters")
    }
}
