package com.awesome.foodrunner.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val id: String,
    @SerialName("image_url")
    val imageUrl: String,
    val name: String
)