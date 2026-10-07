import com.android.build.api.dsl.LibraryExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.4.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:9.1.1")
        classpath("com.github.recloudstream:gradle:81b1d424d2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    extensions.getByType<CloudstreamExtension>().apply {
        setRepo("https://github.com/darknesslord19/Play")
        requiresResources = false
    }

    extensions.getByType<LibraryExtension>().apply {
        namespace = "com.tavsiyefilmizle"
        compileSdk = 36
        defaultConfig {
            minSdk = 21
        }
        lint { targetSdk = 36 }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    extensions.findByType<JavaPluginExtension>()?.toolchain?.languageVersion?.set(JavaLanguageVersion.of(17))

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-Xno-call-assertions",
                "-Xno-param-assertions",
                "-Xno-receiver-assertions",
                "-Xjspecify-annotations=ignore",
                "-Xskip-metadata-version-check"
            )
        }
    }

    dependencies {
        add("cloudstream", "com.lagradost:cloudstream3:pre-release")
        add("implementation", kotlin("stdlib"))
        add("implementation", "com.github.Blatzar:NiceHttp:0.4.18")
        add("implementation", "org.jsoup:jsoup:1.22.2")
        add("implementation", "org.jspecify:jspecify:1.0.0")
        add("implementation", "com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
        add("implementation", "com.fasterxml.jackson.core:jackson-databind:2.13.1")
        add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
        add("implementation", "org.mozilla:rhino:1.8.1")
        add("implementation", "me.xdrop:fuzzywuzzy:1.4.0")
        add("implementation", "com.google.code.gson:gson:2.14.0")
        add("implementation", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
        add("implementation", "org.bouncycastle:bcpkix-jdk18on:1.84")
    }
}

tasks.named("clean") {
    delete(layout.buildDirectory)
}
