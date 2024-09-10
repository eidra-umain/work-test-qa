package com.awesome.foodrunner.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName("delivery_time_minutes")
    val deliveryTimeMinutes: Int,
    val filterIds: List<String>,
    val id: String,
    @SerialName("image_url")
    val imageUrl: String,
    val name: String,
    val rating: Double
)