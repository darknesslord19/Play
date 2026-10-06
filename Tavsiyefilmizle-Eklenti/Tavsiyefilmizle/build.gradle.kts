import com.lagradost.cloudstream3.gradle.CloudstreamExtension

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.lagradost.cloudstream3.gradle")
}

android {
    namespace = "com.tavsiyefilmizle"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

cloudstream {
    setRepoUrl("https://github.com/kullaniciadi/Tavsiyefilmizle-Eklenti")
}

dependencies {
    val cloudstreamVersion = "3.0.0"
    compileOnly("com.github.recloudstream:cloudstream:$cloudstreamVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    compileOnly("org.jsoup:jsoup:1.16.2")
}
