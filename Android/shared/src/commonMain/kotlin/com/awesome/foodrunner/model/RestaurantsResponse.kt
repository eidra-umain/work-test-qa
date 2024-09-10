package com.awesome.foodrunner.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponse(
    val restaurants: List<Restaurant>
)