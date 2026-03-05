import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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
            implementation(project(":core")) {
                exclude(group = "com.google.guava", module = "listenablefuture")
            }
            implementation(libs.bundles.camera)
            implementation("com.google.guava:listenablefuture:1.0")
        }
        commonMain.dependencies {
            implementation(project(":cxui")) {
                exclude(group = "com.google.guava", module = "listenablefuture")
            }
            implementation(project(":core")) {
                exclude(group = "com.google.guava", module = "listenablefuture")
            }
            implementation(libs.backhandler)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.peekaboo)
            implementation(libs.coil.network.ktor)
        }
        iosMain.dependencies {
            implementation(project(":core"))
        }
    }
}

android {
    namespace = "com.jottie.camera"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}