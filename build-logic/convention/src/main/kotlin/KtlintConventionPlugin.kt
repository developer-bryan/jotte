import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            extensions.configure<KtlintExtension> {
                version.set("1.4.1") // Keep 1.4.0 + for Kotlin 2.1+ support
                verbose.set(true)
                android.set(true)
                outputToConsole.set(true)
                filter {
                    exclude("**/generated/**")
                    exclude("**/generated/compose/**")
                    include("**/*.kt")
                }
            }
        }
    }
}