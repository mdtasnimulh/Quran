plugins {
    alias(libs.plugins.quran.android.feature)
    alias(libs.plugins.quran.android.library.compose)
}

android {
    namespace = "com.tasnimulhasan.arabicletters"
}

dependencies {
    implementation(libs.coil.kt.compose)
}