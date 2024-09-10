package com.awesome.foodrunner.android.ui.common

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.awesome.foodrunner.android.core.model.FilterWithState
import com.awesome.foodrunner.android.ui.theme.Title2
import com.awesome.foodrunner.android.ui.theme.defaultPadding

@Composable
fun FilterChip(modifier: Modifier = Modifier, filter: FilterWithState, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(
                filter.backgroundColorForCurrentStatus()
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(44.dp),
            painter = rememberAsyncImagePainter(
                model = filter.imageUrl
            ),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = filter.name,
            style = Title2,
            color = filter.textColorForCurrentStatus()
        )
        Spacer(Modifier.width(defaultPadding))
    }
}