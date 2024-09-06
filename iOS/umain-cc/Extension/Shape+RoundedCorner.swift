//
//  Shape+RoundedCorner.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-18.
//

import SwiftUI

// NOTE: This will no longer be needed once we are using Xcode 15, as it introduces .rect, and has backported it for older ios versions as well
struct RoundedCorner: Shape {
    var radius: CGFloat = .infinity
    var corners: UIRectCorner = .allCorners

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: CGSize(width: radius, height: radius))
        return Path(path.cgPath)
    }
}
