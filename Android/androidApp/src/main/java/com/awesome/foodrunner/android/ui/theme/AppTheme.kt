package com.awesome.foodrunner.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun AppTheme(
    isLight: Boolean = !isSystemInDarkTheme(), // There is no dark scheme in design, but just a good practice
    content: @Composable () -> Unit
) {
    val localColors = staticCompositionLocalOf<FoodRunnerColorDefinition> {
        FoodRunnerColorDefinition()
    }
    val colorPalette = FoodRunnerColorDefinition()
    CompositionLocalProvider(localColors provides colorPalette) {
        MaterialTheme(
            colorScheme = resetColors(),
            content = content
        )
    }
}

class FoodRunnerColorDefinition() {
    /**
     * The plan was to make our own color scheme using names from Figma because it's tough to
     * match the style colors with the Material or Material 3 paradigm.
     * But I had to skip this idea because I didn't have enough time.
     * Still, for a design like the provided, it's better to have its own named color scheme
     * that can be used all over the app, like: AppTheme.colors.darkTextColor, or AppTheme.colorScheme.darkTextColor.
     */
}

fun resetColors(): ColorScheme {
    return ColorScheme(
        primary = backgroundColor,
        onPrimary = darkTextColor,
        primaryContainer = backgroundColor,
        onPrimaryContainer = darkTextColor,
        inversePrimary =backgroundColor,
        secondary =backgroundColor,
        onSecondary = darkTextColor,
        secondaryContainer =backgroundColor,
        onSecondaryContainer = darkTextColor,
        tertiary =  secondaryBackgroundColor,
        onTertiary = darkTextColor,
        tertiaryContainer = secondaryBackgroundColor,
        onTertiaryContainer = darkTextColor,
        background = backgroundColor,
        onBackground = darkTextColor,
        surface = backgroundColor,
        onSurface = darkTextColor,
        surfaceVariant =backgroundColor,
        onSurfaceVariant = darkTextColor,
        surfaceTint = backgroundColor,
        inverseSurface =backgroundColor,
        inverseOnSurface =darkTextColor,
        error = negativeColor,
        onError = lightTextColor,
        errorContainer = negativeColor,
        onErrorContainer = lightTextColor,
        outline = secondaryBackgroundColor,
        outlineVariant = secondaryBackgroundColor,
        scrim = darkTextColor,
    )
}