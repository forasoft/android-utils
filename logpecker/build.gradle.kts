@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    id(Plugins.detekt) version (Versions.detektPlugin)
    id(Plugins.checkDependencyUpdates) version(Versions.checkDependencyUpdatesPlugin)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("../config/detekt-config.yml")
}

android {
    namespace = "com.forasoft.androidutils.logpecker"
    compileSdk = 33

    defaultConfig {
        minSdk = 14
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

dependencies {
    // TODO: Add when publishing the library
//    implementation(project(Dependencies.Modules.platformAndroid))

    implementation(Dependencies.jetpackCore)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.startup)
    implementation(Dependencies.coroutines)
}
