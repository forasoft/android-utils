plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)

    id(Plugins.detekt) version (Versions.detektPlugin)
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("../config/detekt-config.yml")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnit()
}

dependencies {
    implementation(Dependencies.coroutines)

    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.truth)
    testImplementation(Dependencies.coroutinesTest)
}
