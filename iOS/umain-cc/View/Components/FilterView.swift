//
//  FilterView.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-18.
//

import SwiftUI

struct FilterView: View {
    let selected: Bool
    let filter: Filter
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            AsyncImage(url: URL(string: filter.imageUrl)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                Color.gray
            }
            .frame(width: 48, height: 48)
            .cornerRadius(24)
            Text(filter.name)
                .font(.uTitle2)
                .foregroundColor(selected ? .lightText : .darkText)
        }
        .padding(.leading, 0)
        .padding(.trailing, 16)
        .padding(.vertical, 0)
        .background(selected ? Color.selected : .background)
        .cornerRadius(24)
        .shadow(color: .black.opacity(0.04), radius: 5, x: 0, y: 4)
        .animation(.easeIn, value: selected)
    }
}

struct FilterView_Previews: PreviewProvider {
    static var previews: some View {
        FilterView(selected: false, filter: .init(id: "1", name: "Top Rated", imageUrl: "https://picsum.photos/48/48"))
            .previewDisplayName("Not selected")

        FilterView(selected: true, filter: .init(id: "1", name: "Top Rated", imageUrl: "https://picsum.photos/48/48"))
            .previewDisplayName("Selected")
    }
}
