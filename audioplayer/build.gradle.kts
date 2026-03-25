import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jotte.convention.detekt)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-tooling:1.10.0-rc02")
        }
        commonMain.dependencies {
            implementation(project(":cxui"))
            implementation(project(":core"))
            implementation(project(":data"))
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.datetime)
            implementation(libs.compottie)
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0-rc02")
            implementation(compose.material3)
            api(libs.coil.compose.core)
            api(libs.coil.compose)
            api(libs.coil.mp)
        }
    }
}

android {
    namespace = "com.jotte.audioplayer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

