buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id(Plugins.androidApplication) version(Versions.Plugins.androidGradle) apply false
    id(Plugins.androidLibrary) version(Versions.Plugins.androidGradle) apply false
    id(Plugins.kotlinAndroid) version(Versions.Kotlin.kotlin) apply false
    id(Plugins.kotlinJvm) version(Versions.Kotlin.kotlin) apply false

    id(Plugins.checkDependencyUpdates) version(Versions.Plugins.checkDependencyUpdates)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
