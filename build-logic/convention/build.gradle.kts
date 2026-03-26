plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.detekt)
    implementation(libs.ktlint)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("detektPlugin") {
            id = libs.plugins.jotte.convention.detekt.get().pluginId
            implementationClass = "DetektConventionPlugin"
        }
        register("ktlintPlugin") {
            id = libs.plugins.jotte.convention.ktlint.get().pluginId
            implementationClass = "KtlintConventionPlugin"
        }
    }
}