plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    id(Plugins.detekt) version Versions.detektPlugin
    id(Plugins.checkDependencyUpdates) version Versions.checkDependencyUpdatesPlugin

    id(Plugins.mavenPublish)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("release"))
                artifactId = "ui-view"
            }
        }
    }
}

kotlin {
    explicitApi()
}

android {
    namespace = "com.forasoft.androidutils.ui.view"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = 16
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(Dependencies.jetpackCore)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.truth)
    testImplementation(Dependencies.robolectric)

    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espresso)
    androidTestImplementation(Dependencies.truth)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.from("../../config/detekt-config.yml")
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
