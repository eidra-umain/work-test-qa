package com.awesome.foodrunner.network

import com.awesome.foodrunner.model.Filter
import com.awesome.foodrunner.model.Restaurant
import com.awesome.foodrunner.model.RestaurantStatus
import com.awesome.foodrunner.model.RestaurantsResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class FoodRunnerAPI(private val httpClient: HttpClient) {
    suspend fun getRestaurants(): List<Restaurant> {
        return GetRestaurants().call<RestaurantsResponse>(httpClient)?.restaurants ?: emptyList()
    }

    suspend fun getRestaurantOpenStatus(restaurantId: String): RestaurantStatus? {
        return GetRestaurantStatus(listOf(restaurantId)).call(httpClient)
    }

    suspend fun getFilter(filterId: String): Filter? {
        return GetFilter(listOf(filterId)).call(httpClient)
    }
}