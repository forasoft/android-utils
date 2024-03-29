object Versions {

    const val compileSdk = 34
    const val targetSdk = 34

    object Plugins {
        const val androidGradle = "8.2.0"
        const val detekt = "1.23.3"
        const val checkDependencyUpdates = "1.5.0"
    }

    object Kotlin {
        const val kotlin = "1.9.20"
        const val coroutines = "1.7.3"
    }

    object Jetpack {
        const val core = "1.12.0"
        const val appCompat = "1.6.1"
        const val activity = "1.8.1"
        const val navigation = "2.7.5"
        const val dataStore = "1.0.0"
        const val startup = "1.1.1"
        const val junit = "1.1.5"
        const val espresso = "3.5.1"

        object Compose {
            const val bom = "2023.10.01"
            const val compiler = "1.5.5"
        }
    }

    const val okHttp = "4.12.0"
    const val webRtc = "104.5112.10"

    const val timber = "5.0.1"

    const val junit = "4.13.2"
    const val truth = "1.1.5"
    const val robolectric = "4.11.1"

}
