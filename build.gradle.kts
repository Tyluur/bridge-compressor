import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.8.0"
}

group = "ca.bridge-io"
version = "1.0-SNAPSHOT"

application {
	mainClass.set("ca.bridge-io.BootstrapKt") // Replace with the fully qualified name of your main class
}

repositories {
	mavenCentral()
	mavenLocal()
	maven(url = "https://dl.bintray.com/michaelbull/maven")
}

dependencies {
	//Logging
	implementation("org.slf4j:slf4j-api:2.0.5")
	implementation("ch.qos.logback:logback-classic:1.2.9")
	implementation(
		group = "com.michael-bull.kotlin-inline-logger", name = "kotlin-inline-logger-jvm", version = "1.0.2"
	)

	// Unzip Library
	implementation(group = "net.lingala.zip4j", name = "zip4j", version = "2.11.1")

	// Co Routine Usage
	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.3.7")

	testImplementation(kotlin("test"))
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11)) // Change this to match your project's target JVM version
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

tasks.test {
	useJUnitPlatform()
}
