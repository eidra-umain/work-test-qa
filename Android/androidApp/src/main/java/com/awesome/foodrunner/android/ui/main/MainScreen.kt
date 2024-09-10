package com.awesome.foodrunner.android.ui.main

import android.app.Activity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.awesome.foodrunner.android.R
import com.awesome.foodrunner.android.ui.common.FilterChip
import com.awesome.foodrunner.android.ui.common.RestaurantCard
import com.awesome.foodrunner.android.ui.details.RestaurantDetailsContent
import com.awesome.foodrunner.android.ui.theme.backgroundColor
import com.awesome.foodrunner.android.ui.theme.defaultPadding
import com.awesome.foodrunner.android.ui.viewModel.LoadingState
import com.awesome.foodrunner.android.ui.viewModel.MainScreenViewData
import com.awesome.foodrunner.android.ui.viewModel.MainScreenViewModel

class MainScreen(private val viewModel: MainScreenViewModel) {
    private val viewData: MainScreenViewData get() = viewModel.viewData
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {
        val scaffoldState = rememberBottomSheetScaffoldState()
        val context = LocalContext.current
        SideEffect {
            (context as Activity).window.statusBarColor = backgroundColor.toArgb()
            context.window.navigationBarColor = backgroundColor.toArgb()
        }

        BoxWithConstraints {
            val bottomSheetHeight: Dp by animateDpAsState(
                targetValue = if (viewData.focusedRestaurant == null) 0.dp else maxHeight,
                label = ""
            )
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = bottomSheetHeight,
                sheetShape = RectangleShape,
                sheetDragHandle = {},
                sheetContent = {
                    RestaurantDetailsContent(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }) { innerPadding ->
                MainContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    @Composable
    private fun MainContent(modifier: Modifier) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopBarContent()
            },
            content = {paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    when (viewData.loadingState) {
                        LoadingState.Idle -> {}
                        LoadingState.Error -> ErrorContent()
                        LoadingState.Loading -> OverlayContent()
                        LoadingState.Done -> RestaurantListContent()
                    }
                }
            }
        )
    }

    @Composable
    private fun ErrorContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = viewData.loadingMessageRes)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onError,
                    disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onError
                ),
                onClick = {
                    viewModel.getAllRestaurants()
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.label_retry),
                    )
                }
            }
        }
    }

    @Composable
    private fun TopBarContent() {
        println("TopBar() recomposed")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Spacer(Modifier.width(defaultPadding))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun OverlayContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                modifier = Modifier
                    .padding(top = defaultPadding),
                text = stringResource(id = viewData.loadingMessageRes)
            )
        }
    }

    @Composable
    private fun RestaurantListContent() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FiltersContent()
            RestaurantsContent()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun RestaurantsContent() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(defaultPadding),
            verticalArrangement = Arrangement.spacedBy(defaultPadding)
        ) {
            items(items = viewData.restaurants.toList()) { restaurant ->
                RestaurantCard(
                    modifier = Modifier
                        .animateItemPlacement(),
                    restaurant = restaurant,
                    tags = viewModel.tagsAsStringsFor(restaurant)
                ) {
                    viewModel.showRestaurantDetails(restaurant)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun FiltersContent() {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = defaultPadding,
                vertical = 6.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(defaultPadding)
        ) {
            items(
                items = viewData.filters,
                key = {
                    it.id
                }
            ){ filter ->
                FilterChip(
                    modifier = Modifier
                        .animateItemPlacement(),
                    filter = filter
                ) {
                    viewModel.toggleFilter(filter.id)
                }
            }
        }

    }

}