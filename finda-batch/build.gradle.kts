plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management")
}

description = "finda-batch"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(Dependencies.JACKSON)
    implementation(Dependencies.KAFKA)
    implementation(Dependencies.QUARTZ)
    implementation(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.SPRING_DATA_JPA)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
