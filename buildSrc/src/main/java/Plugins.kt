object Plugins {

    object BuildScript {
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"

    const val detekt = "io.gitlab.arturbosch.detekt"
    const val checkDependencyUpdates = "name.remal.check-dependency-updates"

}
