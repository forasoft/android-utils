object Dependencies {

    object Modules {
        const val platformAndroid = ":platform:android"
    }

    const val jetpackCore = "androidx.core:core-ktx:${Versions.jetpackCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val startup = "androidx.startup:startup-runtime:${Versions.startup}"

    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeRuntime = "androidx.compose.runtime:runtime"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeAnimation = "androidx.compose.animation:animation"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val webRtc = "com.github.webrtc-sdk:android:${Versions.webRtc}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val junit = "junit:junit:${Versions.junit}"
    const val androidJunit = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

}
