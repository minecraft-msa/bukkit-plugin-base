plugins {
    alias(libs.plugins.shadow) apply false
}

subprojects {
    with(pluginManager) {
        apply(rootProject.libs.plugins.shadow.get().pluginId)
    }
    dependencies {
        implementation(rootProject.projects.core)
    }
}