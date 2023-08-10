object Versions {

    const val compileSdk = 33
    const val targetSdk = 33

    object Plugins {
        const val androidGradle = "7.4.1"
        const val detekt = "1.23.1"
        const val checkDependencyUpdates = "1.5.0"
    }

    object Kotlin {
        const val kotlin = "1.9.0"
        const val coroutines = "1.7.3"
    }

    object Jetpack {
        const val core = "1.10.1"
        const val appCompat = "1.6.1"
        const val navigation = "2.7.0"
        const val startup = "1.1.1"
        const val junit = "1.1.5"
        const val espresso = "3.5.1"

        object Compose {
            const val bom = "2023.08.00"
            const val compiler = "1.5.1"
        }
    }

    const val okHttp = "4.11.0"
    const val webRtc = "104.5112.10"

    const val timber = "5.0.1"

    const val junit = "4.13.2"
    const val truth = "1.1.5"
    const val robolectric = "4.10.3"

}
