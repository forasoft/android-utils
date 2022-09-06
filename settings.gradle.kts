pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI.create("https://jitpack.io") }
    }
}
rootProject.name = "AndroidUtils"
include(":clean")
include(":platfrom:android")
include(":kotlin:common")
include(":kotlin:coroutines")
include(":webrtc")
