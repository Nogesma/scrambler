plugins {
    kotlin("jvm") version "2.2.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.worldcubeassociation.tnoodle:lib-scrambles:0.19.2")
    implementation("org.mongodb:mongodb-driver-sync:5.6.0")
    implementation("com.google.code.gson:gson:2.13.2")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}

tasks.jar.configure {
    manifest {
        attributes(mapOf("Main-Class" to "MainKt"))
    }

    from(configurations["runtimeClasspath"].map { file: File ->
       if (file.isDirectory) file else zipTree(file.absoluteFile)
    })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}