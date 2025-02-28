dependencies {
    implementation(projects.modules.pluginChat)
    implementation(projects.infrastructure.infrastructureKafka)
}

tasks.shadowJar {
    archiveBaseName.set("lobby")
    archiveClassifier.set("")
}