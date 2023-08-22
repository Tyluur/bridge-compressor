import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.8.0"
}

group = "ca.bridge_io"
version = "1.0-SNAPSHOT"

application {
	mainClass.set("ca.bridge_io.BootstrapKt") // Replace with the fully qualified name of your main class
}

repositories {
	mavenCentral()
	mavenLocal()
	maven(url = "https://dl.bintray.com/michaelbull/maven")
}

dependencies {
	implementation("org.slf4j:slf4j-api:2.0.5")
	implementation("ch.qos.logback:logback-classic:1.2.9")
	implementation("net.lingala.zip4j", "zip4j", "2.11.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
	implementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.5")
	runtimeOnly("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger-jvm:1.0.5")
	testImplementation(kotlin("test"))
	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
}

tasks.jar {
	manifest {
		attributes["Main-Class"] = "ca.bridge_io.BootstrapKt"
	}
	from(sourceSets.main.get().output)
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

tasks.test {
	useJUnitPlatform()
}
