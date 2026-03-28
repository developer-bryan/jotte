plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

copy {
    val hooksDir = "$rootDir/.git/hooks"
    println("⬇️ Installing pre-push installed @ $hooksDir")
    from("config/pre-push")
    into(hooksDir)
    filePermissions {
        user {
            read = true
            write = true
            execute = true
        }
        group {
            read = true
            execute = true
        }
        other {
            read = true
            execute = true
        }
    }
    println("pre push hook installed ✅")
}

tasks.register("ktlintCheckAll") {
    dependsOn(subprojects.mapNotNull { it.tasks.findByName("ktlintCheck") })
}