package com.awesome.foodrunner.android.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.awesome.foodrunner.android.R
import com.awesome.foodrunner.android.core.helper.pluralise
import com.awesome.foodrunner.android.ui.theme.Footer1
import com.awesome.foodrunner.android.ui.theme.Footer2
import com.awesome.foodrunner.android.ui.theme.Subtitle1
import com.awesome.foodrunner.android.ui.theme.Title1
import com.awesome.foodrunner.android.ui.theme.defaultPadding
import com.awesome.foodrunner.android.ui.theme.secondaryBackgroundColor
import com.awesome.foodrunner.android.ui.theme.subtitleColor
import com.awesome.foodrunner.model.Restaurant

@Composable
fun RestaurantCard(modifier: Modifier, restaurant: Restaurant, tags: String, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .requiredHeight(196.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondaryBackgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                contentScale = Crop,
                painter = rememberAsyncImagePainter(
                    model = restaurant.imageUrl
                ),
                contentDescription = null
            )
            RestaurantDetails(
                modifier = Modifier
                    .fillMaxWidth(),
                restaurant = restaurant,
                tags = tags
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RestaurantDetails(modifier: Modifier, restaurant: Restaurant, tags: String) {
    Row(
        modifier = modifier
            .padding(8.dp   )
    ) {
        Column(
            modifier = Modifier
                .weight(1f, true)
        ) {
            Text(
                text = restaurant.name,
                style = Title1,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                modifier = Modifier
                    .basicMarquee(
                        iterations = Int.MAX_VALUE
                    ),
                text = tags,
                style = Subtitle1,
                color = subtitleColor
            )
            Spacer(Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = null
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = restaurant.deliveryTimeMinutes.pluralise(
                        stringResource(id = R.string.label_minute),
                        stringResource(id = R.string.label_minutes)
                    ),
                    style = Footer1,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Spacer(Modifier.width(defaultPadding))
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = restaurant.rating.toString(),
                    style = Footer2,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}