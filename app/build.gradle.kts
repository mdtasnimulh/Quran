import com.tasnimulhasan.quran.AppConfig
import com.tasnimulhasan.quran.QuranBuildType

plugins {
    alias(libs.plugins.quran.android.application)
    alias(libs.plugins.quran.android.application.compose)
    alias(libs.plugins.quran.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("app_credential/ghuraba.jks")
            storePassword = "Ghuraba@#$123"
            keyAlias = "ghuraba"
            keyPassword = "Ghuraba@#$123"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = QuranBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = QuranBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.named("debug").get()
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /*** "Al Asma Ul Husna (ALl the Beautiful Names and Attribute belong to Allah Subahnahu Ta'Ala" ***/
    implementation(projects.fetatureAlAsmaUlHusna)

    /*** Core Modules ***/
    with(projects.core) {
        implementation(common)
        implementation(data)
        implementation(database)
        implementation(designSystem)
        implementation(di)
        implementation(domain)
        implementation(model.apiResponse)
        implementation(model.entity)
        implementation(notification)
        implementation(datastore)
        implementation(ui)
    }
    /*** Core Modules ***/

    /*** Feature Modules ***/
    with(projects) {
        implementation(feature.home)
        implementation(feature.quran)
        implementation(feature.suraDetails)
        implementation(feature.calendar)
        implementation(feature.compass)
        implementation(feature.arabicLetters)
        implementation(feature.suggestion)
        implementation(feature.settings)
        implementation(feature.about)
        implementation(feature.dua)
        implementation(feature.quranRecitation)

        /* Hadith Modules */
        implementation(featureHadith.hadith)
        implementation(featureHadith.hadithChapterrs)
        implementation(featureHadith.hadithDetails)
    }
    /*** Feature Modules ***/

    /*** Other Dependencies ***/
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.animation.compose)
    implementation(libs.androidx.window.core)
    implementation(libs.coil.kt)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.glide.compose)
    implementation(libs.androidx.palette.compose)
    implementation(libs.material3.expressive)

    implementation(libs.play.services.location)
    ksp(libs.hilt.compiler)
    implementation(libs.timber)

    implementation(libs.bundles.media3.dependencies)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    /*** Other Dependencies ***/
}