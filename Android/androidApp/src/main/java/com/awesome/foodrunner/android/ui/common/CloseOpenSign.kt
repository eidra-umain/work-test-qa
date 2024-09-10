package com.awesome.foodrunner.android.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awesome.foodrunner.android.core.model.CloseOpenStatus
import com.awesome.foodrunner.android.ui.theme.Title1

@Composable
fun CloseOpenSign(modifier: Modifier = Modifier, state: CloseOpenStatus) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        if (state == CloseOpenStatus.Unknown) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = state.displayText(),
                style = Title1,
                color = state.color()
            )
        }
    }
}