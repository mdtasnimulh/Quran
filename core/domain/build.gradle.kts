plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tasnimulhasan.domain"
}

dependencies {
    api(projects.core.model.entity)
    implementation(projects.core.common)

    implementation(libs.kotlin.coroutines)
    implementation(libs.javax.inject)
    implementation(libs.bundles.androidx.core.dependencies)
    implementation(libs.bundles.androidx.material.dependencies)
    implementation(libs.bundles.room.dependencies)
    implementation(libs.bundles.media3.dependencies)
}