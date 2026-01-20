plugins {
    alias(libs.plugins.quran.android.feature)
    alias(libs.plugins.quran.android.library.compose)
}

android {
    namespace = "com.tasnimulhasan.alasmaulhusna"
}

dependencies {
    implementation(libs.coil.kt.compose)
    implementation(libs.bundles.media3.dependencies)
}