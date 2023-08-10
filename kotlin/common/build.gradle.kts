plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)

    id(Plugins.detekt) version Versions.Plugins.detekt
    id(Plugins.checkDependencyUpdates) version Versions.Plugins.checkDependencyUpdates

    id(Plugins.mavenPublish)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("java"))
                artifactId = "kotlin-common"
            }
        }
    }
}

kotlin {
    explicitApi()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnit()
}

dependencies {
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
