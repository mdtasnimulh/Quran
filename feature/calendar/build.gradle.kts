plugins {
    alias(libs.plugins.quran.android.feature)
    alias(libs.plugins.quran.android.library.compose)
}

android {
    namespace = "com.example.calendar"
}

dependencies {
    implementation(libs.play.services.location)
}