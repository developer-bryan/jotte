import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildConfig)
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
            implementation(libs.androidx.activity.compose)
            implementation(libs.media3.transformer)
        }

        commonMain.dependencies {
            implementation(libs.datetime)
            api(libs.filekt.core)
            api(libs.filekt.dialogs)
            api(libs.filekt.dialogs.compose)
            api(libs.filekt.coil)
            api(libs.serialization)
            api(libs.viewmodel.compose)
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            implementation(libs.media3.exoplayer)
        }
        commonTest.dependencies {
            implementation(libs.datetime)
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.jotte.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

buildConfig {
    val props = Properties().apply {
        file("$rootDir/jotte.properties")
            .inputStream()
            .use(this::load)
    }

    packageName("com.jotte.core")
    useKotlinOutput { internalVisibility = false }

    buildConfigField("APP_VERSION", props.getProperty("VERSION_STRING"))
}
