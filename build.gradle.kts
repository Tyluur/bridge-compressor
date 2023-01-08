import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
}

group = "com.tyluur"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven(url = "https://repo.maven.apache.org/maven2")
    maven(url = "https://dl.bintray.com/michaelbull/maven")
}

dependencies {
    //Logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation(
        group = "com.michael-bull.kotlin-inline-logger",
        name = "kotlin-inline-logger-jvm",
        version = "1.0.2"
    )

    // Unzip Library
    implementation(group = "net.lingala.zip4j", name = "zip4j", version = "2.11.1")

    // Co Routine Usage
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.3.7")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}