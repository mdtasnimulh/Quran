plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "com.tasnimulhasan.entity"
}

dependencies {
    implementation(libs.room.common)
    implementation(libs.gson)
}