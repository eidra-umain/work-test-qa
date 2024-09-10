package com.awesome.foodrunner.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantStatus(
    @SerialName("is_currently_open")
    val isCurrentlyOpen: Boolean,
    @SerialName("restaurant_id")
    val restaurantId: String
)