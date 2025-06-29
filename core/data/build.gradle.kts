plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.tasnimulhasan.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.di)
    implementation(projects.core.database)
    implementation(projects.core.common)
    implementation(projects.core.model.entity)
    implementation(projects.core.model.apiResponse)
    implementation(projects.core.notification)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlin.coroutines)
    implementation(libs.bundles.network.dependencies)
    implementation(libs.bundles.rxjava3.dependencies)
    testImplementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.media3.dependencies)
}