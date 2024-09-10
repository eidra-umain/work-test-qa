package com.awesome.foodrunner

import com.awesome.foodrunner.network.FoodRunnerAPI
import io.ktor.client.*
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class FoodRunnerAPITest {
    @Test
    fun testGetRestaurants() = runTest {
        val jsonResponse = """
            {
                "restaurants": [
                    {
                        "delivery_time_minutes": 30,
                        "filterIds": ["1", "2"],
                        "id": "restaurant-123",
                        "image_url": "https://awesome.com/image.png",
                        "name": "Test Restaurant",
                        "rating": 4.5
                    }
                ]
            }
        """.trimIndent()
        val mockEngine = MockEngine { request ->
            assertEquals("/api/v1/restaurants", request.url.encodedPath)
            respond(
                content = ByteReadChannel(jsonResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val client = HttpClient(mockEngine) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
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

        val api = FoodRunnerAPI(client)

        val restaurants = api.getRestaurants()

        assertEquals(1, restaurants.size)
        assertEquals("restaurant-123", restaurants.first().id)
        assertEquals("Test Restaurant", restaurants.first().name)
    }

    //And other fun tests... I hope mockk would be here :(
}