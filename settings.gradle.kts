pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "TestTests"
include(":app")
include(":feature:courses:ui")
include(":feature:courses:domain")
include(":feature:courses:data")
include(":common")
include(":feature:auth:ui")
include(":feature:auth:data")
include(":feature:auth:domain")
