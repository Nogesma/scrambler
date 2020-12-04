import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.20"
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
  mavenCentral()
  maven(url = "https://dl.bintray.com/thewca/tnoodle-lib")
}

dependencies {
  implementation("org.worldcubeassociation.tnoodle:lib-scrambles:0.18.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
  implementation("org.jetbrains.kotlinx:atomicfu-jvm:0.14.4")
  implementation("com.github.ajalt.clikt:clikt:3.0.1")
  implementation("org.mongodb:mongodb-driver-sync:4.1.1")
  implementation("com.google.code.gson:gson:2.8.6")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }
  shadowJar {
    archiveClassifier.set("")
  }
}

application {
  mainClassName = "MainKt"
}
