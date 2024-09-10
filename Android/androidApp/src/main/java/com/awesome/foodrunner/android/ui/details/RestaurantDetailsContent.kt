package com.awesome.foodrunner.android.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.awesome.foodrunner.android.R
import com.awesome.foodrunner.android.core.model.CloseOpenStatus
import com.awesome.foodrunner.android.core.model.RestaurantMediator
import com.awesome.foodrunner.android.ui.common.CloseOpenSign
import com.awesome.foodrunner.android.ui.viewModel.MainScreenViewModel
import com.awesome.foodrunner.android.ui.theme.Headline1
import com.awesome.foodrunner.android.ui.theme.Headline2
import com.awesome.foodrunner.android.ui.theme.Subtitle1
import com.awesome.foodrunner.android.ui.theme.backgroundColor
import com.awesome.foodrunner.android.ui.theme.darkTextColor
import com.awesome.foodrunner.android.ui.theme.defaultPadding
import com.awesome.foodrunner.android.ui.theme.secondaryBackgroundColor
import com.awesome.foodrunner.android.ui.theme.subtitleColor
import com.awesome.foodrunner.model.Restaurant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantDetailsContent(modifier: Modifier, viewModel: MainScreenViewModel) {
    val viewData = viewModel.viewData

    val restaurantMediator = viewData.focusedRestaurant ?: return
    val restaurant = restaurantMediator.restaurant
    val openStatus = restaurantMediator.openStatus

    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = rememberAsyncImagePainter(
                model = restaurant.imageUrl
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        DetailsContent(
            restaurant = restaurant,
            openStatus = openStatus,
            viewModel = viewModel
        )

        ChevronContent(
            viewModel = viewModel
        )

    }
}

@Composable
private fun ChevronContent(viewModel: MainScreenViewModel) {
    Box(
        modifier = Modifier
            .size(
                width = 22.dp + 25.dp,
                height = 84.dp
            )
            .clickable {
                viewModel.hideRestaurantDetails()
            },
        contentAlignment = Alignment.BottomEnd,
    ) {
        Image(
            modifier = Modifier.size(25.dp, 19.dp),
            painter = painterResource(id = R.drawable.chevron),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsContent(restaurant: Restaurant, openStatus: CloseOpenStatus, viewModel: MainScreenViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = defaultPadding)
            .padding(top = 175.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondaryBackgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(defaultPadding)
        ) {
            Box(
                modifier = Modifier
                    .height(42.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = restaurant.name,
                    style = Headline1,
                    color = darkTextColor
                )
            }

            Box(
                modifier = Modifier
                    .height(35.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(Int.MAX_VALUE),
                    text = viewModel.tagsAsStringsFor(restaurant),
                    style = Headline2,
                    color = subtitleColor
                )
            }

            CloseOpenSign(
                modifier = Modifier
                    .height(35.dp),
                state = openStatus
            )
        }
    }
}