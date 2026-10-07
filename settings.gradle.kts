pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Play"

File(rootDir, ".").listFiles()?.filter { dir ->
    dir.isDirectory && !dir.name.startsWith(".") && File(dir, "build.gradle.kts").exists()
}?.forEach { dir ->
    include(":${dir.name}")
}
