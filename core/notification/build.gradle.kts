plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.hilt)
}

android {
    namespace = "com.tasnimulhasan.notification"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.bundles.media3.dependencies)

    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.runtime)
}