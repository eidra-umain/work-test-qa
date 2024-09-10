package com.awesome.foodrunner.android.ui.theme;

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Headline1: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 24.sp,
        lineHeight = 16.sp
    )

val Headline2: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 16.sp
    )

val Title1: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 18.sp,
        lineHeight = 16.sp
    )

val Title2: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    )

val Subtitle1: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold
    )

val Footer1: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium
    )

// Undefined style for rating
val Footer2: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    )