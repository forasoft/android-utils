buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(BuildScriptPlugins.androidGradle)
        classpath(BuildScriptPlugins.kotlin)
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
