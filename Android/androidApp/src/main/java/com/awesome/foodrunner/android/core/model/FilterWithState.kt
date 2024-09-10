package com.awesome.foodrunner.android.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.awesome.foodrunner.android.ui.theme.darkTextColor
import com.awesome.foodrunner.android.ui.theme.lightTextColor
import com.awesome.foodrunner.android.ui.theme.selectedColor
import com.awesome.foodrunner.android.ui.theme.unselectedColor
import com.awesome.foodrunner.model.Filter

data class FilterWithState(
    val id: String,
    val imageUrl: String,
    val name: String,
    val isActive: Boolean = false
) {
    @Composable
    fun backgroundColorForCurrentStatus(): Color =
        if (isActive) {
            selectedColor
        } else {
            unselectedColor
        }

    @Composable
    fun textColorForCurrentStatus(): Color =
        if (isActive) {
            lightTextColor
        } else {
            darkTextColor
        }

    companion object {
        fun fromFilter(filter: Filter): FilterWithState {
            return FilterWithState(
                id = filter.id,
                imageUrl = filter.imageUrl,
                name = filter.name
            )
        }
    }
}