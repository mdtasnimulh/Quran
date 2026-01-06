@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://oss.jfrog.org/libs-snapshot")
        maven("https://www.jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://www.jitpack.io")
        maven("https://oss.jfrog.org/libs-snapshot")
    }
}

rootProject.name = "Quran"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

/*** Core Modules ***/
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:design-system")
include(":core:di")
include(":core:domain")
include(":core:model:api-response")
include(":core:model:entity")
include(":core:ui")
include(":core:notification")
include(":core:datastore")
/*** Core Modules ***/

/*** Feature Modules ***/
include(":feature:home")
include(":feature:quran")
include(":feature:hadith")
include(":feature:sura-details")
include(":feature:calendar")
include(":feature:compass")
include(":feature:arabic-letters")
include(":feature:suggestion")
/*** Feature Modules ***/