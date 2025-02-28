rootProject.name = extra["project.name"].toString()

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

singleProject("bukkit-plugin-core", "core")
project("bukkit-infrastructure", "infrastructure")
project("bukkit-plugin-modules", "modules", "plugin")
project("bukkit-servers", "servers", "server")

fun singleProject(parentDirectory: String, name: String) {
    val modulePath = ":$name"
    include(modulePath)

    val projectDirectory = rootProject.projectDir
    project(modulePath).projectDir = projectDirectory.resolve(parentDirectory)
}

fun project(parentDirectory: String, parentModuleName: String, prefix: String? = null) {
    singleProject(parentDirectory, parentModuleName)

    val projectDirectory = rootProject.projectDir
    val childModuleDirectories = projectDirectory
        .resolve(parentDirectory)
        .listFiles()
        ?.filter { it.isDirectory && it.resolve("build.gradle.kts").exists() } ?: emptyList()
    childModuleDirectories.forEach { childModuleDirectory ->
        val modulePath = if (prefix != null) {
            ":$parentModuleName:$prefix-${childModuleDirectory.name}"
        } else {
            val classifier = parentModuleName.replace(":", "-")
            ":$parentModuleName:$classifier-${childModuleDirectory.name}"
        }
        include(modulePath)
        project(modulePath).projectDir = childModuleDirectory
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")