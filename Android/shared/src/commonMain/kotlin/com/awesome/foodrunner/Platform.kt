package com.awesome.foodrunner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform