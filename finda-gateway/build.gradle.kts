plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "finda"
version = "0.0.1-SNAPSHOT"
description = "finda-gateway"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.SPRING_CLOUD_GATEWAY)
    implementation(Dependencies.JACKSON_KOTLIN)
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // Netty DNS resolver - macOS 개발 환경에서만 필요
//    if (System.getProperty("os.name").lowercase().contains("mac")) {
//        val arch = System.getProperty("os.arch").lowercase()
//        val classifier = if (arch.contains("aarch64") || arch.contains("arm")) {
//            "osx-aarch_64"
//        } else {
//            "osx-x86_64"
//        }
//        runtimeOnly(Dependencies.NETTY_DNS_MACOS) {
//            artifact { this.classifier = classifier }
//        }
//    }

    // Security Common
    implementation(project(":finda-security-common"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${DependencyVersions.SPRING_CLOUD}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
