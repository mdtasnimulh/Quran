plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
}

android {
    namespace = "com.tasnimulhasan.common"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.core.designSystem)
    implementation(projects.core.model.entity)
    implementation(libs.timber)
    implementation(libs.bundles.androidx.core.dependencies)
    implementation(libs.bundles.androidx.material.dependencies)
    implementation(libs.bundles.androidx.lifecycle.dependencies)
    implementation(libs.bundles.androidx.navigation.dependencies)
    implementation(libs.bundles.media3.dependencies)
    implementation(libs.glide.compose)
}