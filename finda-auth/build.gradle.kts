plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.protobuf") version "0.9.4"
}

group = "finda"
version = "0.0.1-SNAPSHOT"
description = "finda-auth"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.SPRING_WEB)
    implementation(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.SPRING_DATA_JPA)
    implementation(Dependencies.UUID_TIME)
    implementation(Dependencies.SPRING_SECURITY)
    implementation(Dependencies.JACKSON)
    implementation(Dependencies.JACKSON_TYPE)
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)
    implementation(Dependencies.SPRING_DATA_REDIS)
    implementation(Dependencies.SPRING_MAIL)
    implementation(Dependencies.SPRING_VALIDITY)
    implementation(Dependencies.LIQUIBASE)
    implementation(Dependencies.GRPC_SERVER)
    implementation(Dependencies.GRPC_PROTOBUF)
    implementation(Dependencies.GRPC_STUB)
    implementation(Dependencies.PROTOBUF_JAVA)
    compileOnly(Dependencies.JAVAX_ANNOTATION)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.58.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
