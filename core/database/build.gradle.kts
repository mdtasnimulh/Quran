plugins {
    alias(libs.plugins.quran.android.library)
    alias(libs.plugins.quran.android.library.compose)
    alias(libs.plugins.quran.android.hilt)
}

android {
    namespace = "com.tasnimulhasan.database"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.room.dependencies)
    implementation(libs.room.common)
    ksp(libs.room.compiler)
}