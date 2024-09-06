//
//  OpenStatus.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

struct OpenStatus: Codable {
    let restaurantId: String
    let isCurrentlyOpen: Bool

    enum CodingKeys: String, CodingKey {
        case restaurantId = "restaurant_id"
        case isCurrentlyOpen = "is_currently_open"
    }
}
