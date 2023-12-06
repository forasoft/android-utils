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
                artifactId = "ui-compose"
            }
        }
    }
}

kotlin {
    explicitApi()
}

android {
    namespace = "com.forasoft.androidutils.ui.compose"
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Jetpack.Compose.compiler
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

// Compose compiler metrics
// Command: ./gradlew assembleRelease -P.enableComposeCompilerReports=true --rerun-tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    // Fix for AGP 7.4.0 bug that doubles args
    val isKaptGenerateStubsTask = this is org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
    if (!isKaptGenerateStubsTask) {
        val path = "${project.buildDir.absolutePath}/compose_metrics"
        kotlinOptions.freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$path"
        )
        kotlinOptions.freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$path"
        )
    }
}

dependencies {
    implementation(project(Dependencies.Modules.platformAndroid))

    implementation(Dependencies.Jetpack.core)
    implementation(Dependencies.Jetpack.Compose.navigation)

    implementation(platform(Dependencies.Jetpack.Compose.bom))
    implementation(Dependencies.Jetpack.Compose.runtime)
    implementation(Dependencies.Jetpack.Compose.ui)
    implementation(Dependencies.Jetpack.Compose.animation)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.truth)
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
