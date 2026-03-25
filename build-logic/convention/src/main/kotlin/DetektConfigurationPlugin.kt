import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DetektConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<DetektExtension> {
                basePath = rootProject.projectDir.absolutePath
                config.setFrom(rootProject.files("config/detekt/detekt.yml"))
                buildUponDefaultConfig = true
                autoCorrect = true
                source.setFrom(
                    "src/commonMain/kotlin",
                    "src/androidMain/kotlin",
                    "src/iosMain/kotlin",
                )
            }
            dependencies {
                "detektPlugins"(libs.findLibrary("detekt.formatting").get())
            }
        }
    }
}
