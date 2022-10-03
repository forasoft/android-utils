object Dependencies {

    object Modules {
        const val platformAndroid = ":platform:android"
    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val jetpackCore = "androidx.core:core-ktx:${Versions.jetpackCore}"

    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"

    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigation}"

    const val startup = "androidx.startup:startup-runtime:${Versions.startup}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val webRtc = "com.github.webrtc-sdk:android:${Versions.webRtc}"

    const val junit = "junit:junit:${Versions.junit}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    const val androidJunit = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val truth = "com.google.truth:truth:${Versions.truth}"

}
