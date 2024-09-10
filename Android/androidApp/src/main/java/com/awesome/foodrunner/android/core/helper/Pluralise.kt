package com.awesome.foodrunner.android.core.helper

fun Int.pluralise(singular: String, plural: String): String {
    return if (kotlin.math.abs(this) == 1) {
        "$this $singular"
    } else {
        "$this $plural" // and zero too
    }
}