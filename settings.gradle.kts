pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "android-ecosystem"

// Core modules (shared libraries)
include(":core:database")
include(":core:map")
include(":core:media")
include(":core:sync")
include(":core:contracts")

// App modules (independent apps)
include(":apps:contacts")
include(":apps:gallery")
include(":apps:map")
