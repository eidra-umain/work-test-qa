package com.awesome.foodrunner.android.core

import com.awesome.foodrunner.android.ui.viewModel.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainScreenViewModel()
    }
}