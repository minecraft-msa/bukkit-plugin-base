dependencies {
    implementation(projects.modules.pluginChat)
    implementation(projects.infrastructure.infrastructureKafka)

    implementation(libs.spring.boot.starter.data.jpa)
    runtimeOnly(libs.mysql)
}

tasks.shadowJar {
    archiveBaseName.set("lobby")
    archiveClassifier.set("")
}