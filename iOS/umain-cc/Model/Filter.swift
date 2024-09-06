//
//  Filter.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

struct Filter: Codable, Identifiable {
    let id: String
    let name: String
    let imageUrl: String

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case imageUrl = "image_url"
    }
}
