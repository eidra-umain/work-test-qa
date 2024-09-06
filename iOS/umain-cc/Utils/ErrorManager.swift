//
//  ErrorManager.swift
//  umain-cc
//
//  Created by Parsa on 2023-09-16.
//

import Foundation

protocol ErrorServiceProtocol {
    func logError(_ error: Error)
}

class ErrorManager: ErrorServiceProtocol {
    static var shared = ErrorManager()

    private init() {}

    func logError(_ error: Error) {
        print(error.localizedDescription)
    }
}
