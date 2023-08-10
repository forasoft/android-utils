plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    id(Plugins.detekt) version Versions.Plugins.detekt
    id(Plugins.checkDependencyUpdates) version Versions.Plugins.checkDependencyUpdates

    id(Plugins.mavenPublish)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("release"))
                artifactId = "clean"
            }
        }
    }
}

kotlin {
    explicitApi()
}

android {
    namespace = "com.forasoft.androidutils.clean"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = 14
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(Dependencies.Kotlin.coroutines)
    implementation(Dependencies.timber)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.truth)
    testImplementation(Dependencies.Kotlin.coroutinesTest)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.from("../config/detekt-config.yml")
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
