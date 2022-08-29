buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.BuildScript.androidGradlePlugin)
        classpath(Plugins.BuildScript.kotlinPlugin)
    }

}

plugins {
    id(Plugins.androidApplication) version(Versions.androidGradlePlugin) apply false
    id(Plugins.androidLibrary) version(Versions.androidGradlePlugin) apply false
    id(Plugins.kotlinAndroid) version(Versions.kotlin) apply false

    id(Plugins.checkDependencyUpdates) version(Versions.checkDependencyUpdatesPlugin)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
