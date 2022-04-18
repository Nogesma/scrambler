import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.10"
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.worldcubeassociation.tnoodle:lib-scrambles:0.18.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
  implementation("org.jetbrains.kotlinx:atomicfu-jvm:0.17.1")
  implementation("com.github.ajalt.clikt:clikt:3.4.0")
  implementation("org.mongodb:mongodb-driver-sync:4.5.1")
  implementation("com.google.code.gson:gson:2.9.0")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }
  shadowJar {
    archiveClassifier.set("")
  }
}

application {
  mainClassName = "MainKt"
}
