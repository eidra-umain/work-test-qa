package com.awesome.foodrunner.android.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.awesome.foodrunner.android.R
import com.awesome.foodrunner.android.ui.theme.darkTextColor
import com.awesome.foodrunner.android.ui.theme.negativeColor
import com.awesome.foodrunner.android.ui.theme.positiveColor
import com.awesome.foodrunner.model.RestaurantStatus

enum class CloseOpenStatus {
    Unknown,
    Open,
    Close;

    @Composable
    fun color(): Color =
        when(this) {
            Unknown -> darkTextColor
            Open -> positiveColor
            Close -> negativeColor
        }

    @Composable
    fun displayText(): String =
        when(this) {
            Unknown -> stringResource(id = R.string.empty)
            Open -> stringResource(id = R.string.label_open)
            Close -> stringResource(id = R.string.lable_close)
        }

    companion object {
        fun from(value: RestaurantStatus?): CloseOpenStatus {
            return if (value?.isCurrentlyOpen == true) {
                Open
            } else {
                Close
            }
        }
    }
}
