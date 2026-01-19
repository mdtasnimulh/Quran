plugins {
    alias(libs.plugins.quran.android.feature)
    alias(libs.plugins.quran.android.library.compose)
}

android {
    namespace = "com.tasnimulhasan.quranrecitation"
}

dependencies {
    implementation(libs.coil.kt.compose)
    implementation(libs.youtube.player)
}