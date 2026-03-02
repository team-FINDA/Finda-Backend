plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.protobuf") version "0.9.4"
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
    implementation(Dependencies.GRPC_CLIENT)
    implementation(Dependencies.GRPC_PROTOBUF)
    implementation(Dependencies.GRPC_STUB)
    implementation(Dependencies.PROTOBUF_JAVA)
    compileOnly(Dependencies.JAVAX_ANNOTATION)
    implementation(Dependencies.FIRE_BASE)
    implementation(Dependencies.KAFKA)
    implementation(Dependencies.SPRING_VALIDITY)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.79.0"
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

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
