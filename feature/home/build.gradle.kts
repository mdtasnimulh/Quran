plugins {
    alias(libs.plugins.quran.android.feature)
    alias(libs.plugins.quran.android.library.compose)
}

android {
    namespace = "com.tasnimulhasan.home"
}

dependencies {
    implementation(libs.accompanist.permissions)
    implementation(libs.coil.kt.compose)
}