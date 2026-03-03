plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "finda"
version = "0.0.1-SNAPSHOT"
description = "finda-notification"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.SPRING_DATA_JPA)
    implementation(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.UUID_TIME)
    implementation(Dependencies.JACKSON)
    implementation(Dependencies.JACKSON_TYPE)
    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.LIQUIBASE)

    // Spring Security
    implementation(Dependencies.SPRING_SECURITY)

    // JWT
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // Security Common
    implementation(project(":finda-security-common"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
