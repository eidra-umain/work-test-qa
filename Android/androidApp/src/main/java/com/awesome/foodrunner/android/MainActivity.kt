package com.awesome.foodrunner.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.awesome.foodrunner.android.core.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightNavigationBars = true
        windowInsetsController.isAppearanceLightStatusBars = true

        startKoin {
            androidContext(this@MainActivity)
            modules(mainModule)
        }

        setContent {
            MainNavHost()
        }
    }
}