plugins {
    kotlin("jvm") version "1.8.10"
    application
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.worldcubeassociation.tnoodle:lib-scrambles:0.18.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:atomicfu-jvm:0.17.2")
    implementation("com.github.ajalt.clikt:clikt:3.4.2")
    implementation("org.mongodb:mongodb-driver-sync:4.6.0")
    implementation("com.google.code.gson:gson:2.9.0")
}

kotlin {
    jvmToolchain(19)
}

application {
    mainClass.set("MainKt")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}
