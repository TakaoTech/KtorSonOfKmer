import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val kotlin_version: String by project
val logback_version: String by project
val ktorfit_version = "2.4.1"

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("de.jensklingenberg.ktorfit") version "2.4.1"
    application
}

application {
    mainClass.set("com.takaotech.client.MainKt")
}

group = "com.takaotech.client"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Ktorfit
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfit_version")

    // Ktor Client
    implementation("io.ktor:ktor-client-cio:3.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")
    implementation("io.ktor:ktor-client-logging:3.0.3")

    // Testing
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
