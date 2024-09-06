//
//  DetailCardView.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-18.
//

import SwiftUI

struct DetailCardView: View {
    let restaurant: Restaurant
    let filters: [Filter]
    let isOpen: Bool?

    var subtitleString: String {
        filters.map(\.name).joined(separator: " • ")
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(restaurant.name)
                .font(.uHeadline1)
                .frame(maxWidth: .infinity, minHeight: 42, alignment: .leading)
                .foregroundColor(.darkText)
            if filters.count > 0 {
                Text(subtitleString)
                    .font(.uHeadline2)
                    .frame(maxWidth: .infinity, minHeight: 35, alignment: .leading)
                    .foregroundColor(.subtitle)
                    .animation(.easeIn, value: filters.count > 0)
            }
            if let isOpen {
                Text(isOpen ? "Open" : "Closed")
                    .font(.uTitle1)
                    .frame(maxWidth: .infinity, minHeight: 35, alignment: .leading)
                    .foregroundColor(isOpen ? .positive : .negative)
                    .animation(.easeIn, value: isOpen)
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity)
        .background(Color.background)
        .cornerRadius(12)
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 4)
    }
}

struct DetailCardView_Previews: PreviewProvider {
    static let mockRestaurant = Restaurant(id: "1", name: "Title", rating: 4.5, filterIds: [], imageUrl: "http://via.placeholder.com/640x360", deliveryTimeMinutes: 9)

    static let mockFilters: [Filter] = [.init(id: "1", name: "Takeout", imageUrl: ""), .init(id: "2", name: "Eat-In", imageUrl: "")]

    static var previews: some View {
        DetailCardView(restaurant: mockRestaurant, filters: [], isOpen: nil)
            .previewDisplayName("No Filter, No status")

        DetailCardView(restaurant: mockRestaurant, filters: mockFilters, isOpen: nil)
            .previewDisplayName("With Filter, No status")

        DetailCardView(restaurant: mockRestaurant, filters: mockFilters, isOpen: true)
            .previewDisplayName("With Filter and status true")

        DetailCardView(restaurant: mockRestaurant, filters: mockFilters, isOpen: false)
            .previewDisplayName("With Filter and status false")
    }
}
