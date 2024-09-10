package com.awesome.foodrunner.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.ktor.util.StringValues

internal class GetRestaurants() : EndpointCaller(HttpMethod.Get, "/restaurants")
internal class GetFilter(segmentParameters: List<String>) : EndpointCaller(HttpMethod.Get, "/filter", segmentParameters = segmentParameters)
internal class GetRestaurantStatus(segmentParameters: List<String>) : EndpointCaller(HttpMethod.Get, "/open", segmentParameters = segmentParameters)

internal sealed class EndpointCaller(
    private val httpMethod: HttpMethod,
    private val apiMethod: String,
    private val segmentParameters: List<String> = emptyList(),
    private val queryParameters: StringValues = StringValues.Empty
) {
    private val httpProtocol = URLProtocol.HTTPS
    private val rootUrl = "food-delivery.umain.io"
    private val apiVersion = "/api/v1"

    suspend inline fun <reified T: Any> call(httpClient: HttpClient) : T? {
        return httpClient.request {
            url {
                protocol = httpProtocol
                host = rootUrl
                path("$apiVersion$apiMethod")
                parameters.appendAll(queryParameters)
                appendPathSegments(segmentParameters, encodeSlash = true)
            }
            method = httpMethod

        }.body() as? T
    }
}
