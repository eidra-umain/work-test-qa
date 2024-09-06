//
//  Restaurant.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

struct Restaurant: Codable, Identifiable {
    let id: String
    let name: String
    let rating: Double
    let filterIds: [String]
    let imageUrl: String
    let deliveryTimeMinutes: Int

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case rating
        case filterIds
        case imageUrl = "image_url"
        case deliveryTimeMinutes = "delivery_time_minutes"
    }
}

struct RestaurantsResponse: Codable {
    let restaurants: [Restaurant]
}
