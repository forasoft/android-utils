@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    id(Plugins.detekt) version (Versions.detektPlugin)
    id(Plugins.checkDependencyUpdates) version(Versions.checkDependencyUpdatesPlugin)

    id(Plugins.mavenPublish)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("../config/detekt-config.yml")
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

android {
    namespace = "com.forasoft.androidutils.webrtc"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = 21
        targetSdk = Versions.targetSdk

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

    testFixtures {
        enable = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    compileOnly(Dependencies.webRtc)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("release"))
            }
        }
    }
}
