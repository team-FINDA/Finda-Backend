plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.protobuf") version "0.9.4"
}

group = "finda"
version = "0.0.1-SNAPSHOT"
description = "finda-volunteer"

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
    implementation(Dependencies.LIQUIBASE)
    implementation(Dependencies.JAVAX_ANNOTATION)

    // JWT
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // Security Common
    implementation(project(":finda-security-common"))

    // gRPC
    implementation(Dependencies.GRPC_CLIENT)
    implementation(Dependencies.GRPC_PROTOBUF)
    implementation(Dependencies.GRPC_STUB)
    implementation(Dependencies.PROTOBUF_JAVA)

    // Apache
    implementation(Dependencies.APACHE_POI)
    implementation(Dependencies.PDF_BOX)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.0"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.59.0"
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

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}
