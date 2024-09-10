package com.awesome.foodrunner.android

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awesome.foodrunner.android.ui.main.MainScreen
import com.awesome.foodrunner.android.ui.viewModel.MainScreenViewModel
import com.awesome.foodrunner.android.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

const val mainScreenDestination = "MainScreenDestination"

@Composable
fun MainNavHost() {
    val navHost = rememberNavController()

    AppTheme {
        NavHost(navController = navHost, startDestination = mainScreenDestination) {
            composable(mainScreenDestination) {
                val viewModel: MainScreenViewModel = getViewModel()
                MainScreen(viewModel).Content()
                LaunchedEffect(viewModel) {
                    viewModel.getAllRestaurants()
                }
            }
        }
    }

}