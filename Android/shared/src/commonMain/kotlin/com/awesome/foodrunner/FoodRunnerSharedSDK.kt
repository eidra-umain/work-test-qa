package com.awesome.foodrunner

import com.awesome.foodrunner.model.Filter
import com.awesome.foodrunner.model.Restaurant
import com.awesome.foodrunner.model.RestaurantStatus
import com.awesome.foodrunner.network.FoodRunnerAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FoodRunnerSharedSDK {
    private val httpClient = HttpClient() {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                prettyPrint = true
                isLenient = true
            })
        }
    }
    private val api = FoodRunnerAPI(httpClient)

    suspend fun getRestaurants(): List<Restaurant> = api.getRestaurants()
    suspend fun getFilter(id: String): Filter? = api.getFilter(id)
    suspend fun getOpenStatus(restaurantId: String): RestaurantStatus? = api.getRestaurantOpenStatus(restaurantId)
}