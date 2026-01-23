import org.jlleitschuh.gradle.ktlint.KtlintExtension

buildscript {
	repositories {
		mavenCentral()
	}
}

	plugins {
		kotlin("jvm") version PluginVersions.JPA_PLUGIN_VERSION
		kotlin("plugin.spring") version PluginVersions.JPA_PLUGIN_VERSION
		kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
		id("org.springframework.boot") version "3.2.1"
		id("io.spring.dependency-management") version "1.1.7"
		id("org.jlleitschuh.gradle.ktlint") version "11.6.0" apply false
	}

allprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	group = "finda"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	apply(plugin = "org.jlleitschuh.gradle.ktlint")

	extensions.configure<KtlintExtension>("ktlint") {
		android.set(false)
		outputToConsole.set(true)
		enableExperimentalRules.set(true)
		ignoreFailures.set(false)
	}
}

tasks.named<Jar>("jar") {
	enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
} // root에서는 직접적인 코드 작성이 없으니 jar을 비활성화한다
