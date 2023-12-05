object Dependencies {

    object Modules {
        const val platformAndroid = ":platform:android"
    }

    object Kotlin {
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Kotlin.coroutines}"
    }

    object Jetpack {
        const val core = "androidx.core:core-ktx:${Versions.Jetpack.core}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Jetpack.appCompat}"
        const val activity = "androidx.activity:activity-ktx:${Versions.Jetpack.activity}"
        const val dataStorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.dataStore}"
        const val startup = "androidx.startup:startup-runtime:${Versions.Jetpack.startup}"
        const val junit = "androidx.test.ext:junit-ktx:${Versions.Jetpack.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Jetpack.espresso}"

        object Compose {
            const val bom = "androidx.compose:compose-bom:${Versions.Jetpack.Compose.bom}"
            const val runtime = "androidx.compose.runtime:runtime"
            const val ui = "androidx.compose.ui:ui"
            const val animation = "androidx.compose.animation:animation"
            const val navigation =
                "androidx.navigation:navigation-compose:${Versions.Jetpack.navigation}"
        }
    }

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val webRtc = "com.github.webrtc-sdk:android:${Versions.webRtc}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val junit = "junit:junit:${Versions.junit}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

}
