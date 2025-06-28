plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
}

android {
    namespace = "com.tasnimulhasan.datastore"
}

dependencies {
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.datastore.preferences)
    api(projects.core.model.entity)
}