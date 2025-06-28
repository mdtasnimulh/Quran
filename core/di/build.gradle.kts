plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
}

android {
    namespace = "com.tasnimulhasan.di"
}

dependencies {
    api(projects.core.datastore)
    implementation(libs.timber)
}