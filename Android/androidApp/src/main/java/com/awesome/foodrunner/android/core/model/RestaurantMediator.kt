package com.awesome.foodrunner.android.core.model

import com.awesome.foodrunner.model.Restaurant

data class RestaurantMediator(
    val restaurant: Restaurant,
    val openStatus: CloseOpenStatus = CloseOpenStatus.Unknown
)