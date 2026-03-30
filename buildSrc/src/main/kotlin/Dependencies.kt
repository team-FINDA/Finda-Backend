object Dependencies {
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    const val UUID_TIME = "com.fasterxml.uuid:java-uuid-generator:${DependencyVersions.UUID_TIME_VERSION}"

    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"

    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${PluginVersions.SPRING_PLUGIN_VERSION}"
    const val SPRING_JDBC = "org.springframework.boot:spring-boot-starter-jdbc"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL}"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_VALIDITY = "org.springframework.boot:spring-boot-starter-validation"

    const val KAFKA = "org.springframework.kafka:spring-kafka"
    const val QUARTZ = "org.springframework.boot:spring-boot-starter-quartz"
    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependencyVersions.JWT}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependencyVersions.JWT}"
    const val JWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JWT}"

    const val SPRING_DATA_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"

    const val SPRING_CLOUD_GATEWAY = "org.springframework.cloud:spring-cloud-starter-gateway"

    const val SPRING_MAIL = "org.springframework.boot:spring-boot-starter-mail"

    const val LIQUIBASE = "org.liquibase:liquibase-core"

    // Netty DNS resolver for MacOS
    const val NETTY_DNS_MACOS = "io.netty:netty-resolver-dns-native-macos:${DependencyVersions.NETTY_DNS}"
    const val GRPC_SERVER = "net.devh:grpc-server-spring-boot-starter:${DependencyVersions.GRPC_STARTER}"
    const val GRPC_PROTOBUF = "io.grpc:grpc-protobuf:${DependencyVersions.GRPC}"
    const val GRPC_STUB = "io.grpc:grpc-stub:${DependencyVersions.GRPC}"
    const val PROTOBUF_JAVA = "com.google.protobuf:protobuf-java:${DependencyVersions.PROTOBUF}"
    const val JAVAX_ANNOTATION = "javax.annotation:javax.annotation-api:${DependencyVersions.JAVAX_ANNOTATION}"
    const val GRPC_CLIENT = "net.devh:grpc-client-spring-boot-starter:${DependencyVersions.GRPC_STARTER}"

    const val FIRE_BASE = "com.google.firebase:firebase-admin:${DependencyVersions.FIRE_BASE}"

    // Apache POI
    const val APACHE_POI = "org.apache.poi:poi-ooxml:${DependencyVersions.APACHE_POI}"
}
