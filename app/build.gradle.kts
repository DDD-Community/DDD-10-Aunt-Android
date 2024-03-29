@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    //id("com.android.application")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id("com.google.gms.google-services")

    //kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.aunt.opeace"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aunt.opeace"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    kapt {
        correctErrorTypes = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.emoji.picker)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //implementation(libs.splash.screen)
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okhttp)

    implementation(libs.coil)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.gms.play.services.auth)

    implementation(libs.kakao.sdk.all)
    implementation(libs.kakao.sdk.user)
}
