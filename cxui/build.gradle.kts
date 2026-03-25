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
            api("org.jetbrains.compose.ui:ui-tooling:1.10.0-rc02")
            implementation(project(":core"))
            implementation(libs.material)
            implementation(libs.splashscreen)
            implementation(libs.media3.exoplayer) // brings in faulty guava breaking ListenableFuture<T>
            implementation(libs.media3.compose)
            implementation(libs.bom.compose)
        }
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":settings:data"))
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.ui)
            api(compose.components.resources)
            api(libs.coil.compose.core)
            api(libs.coil.compose)
            api(libs.coil.mp)
            api("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0-rc02")
            implementation(libs.compose.webview.multiplatform)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
    }

    task("testClasses")
}

android {
    namespace = "com.jotte.cxui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.jotte.cxui"
    generateResClass = auto
}