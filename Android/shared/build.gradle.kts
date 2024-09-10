plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialisation)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.runtime)
                implementation(libs.ktor.client.logging.native)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.ktor.client.mock)
                implementation(libs.ktor.client.logging.native)
                implementation(libs.ktor.client.json)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }

}

android {
    namespace = "com.awesome.foodrunner"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}