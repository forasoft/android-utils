plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)

    id(Plugins.detekt) version (Versions.detektPlugin)
    id(Plugins.checkDependencyUpdates) version(Versions.checkDependencyUpdatesPlugin)

    id(Plugins.mavenPublish)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("../../config/detekt-config.yml")
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

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
