object Dependencies {
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    const val UUID_TIME = "com.fasterxml.uuid:java-uuid-generator:${DependencyVersions.UUID_TIME_VERSION}"

    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"

    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${PluginVersions.SPRING_PLUGIN_VERSION}"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL}"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_VALIDITY = "org.springframework.boot:spring-boot-starter-validation"

    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependencyVersions.JWT}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependencyVersions.JWT}"
    const val JWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JWT}"

    const val SPRING_DATA_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"

    const val SPRING_CLOUD_GATEWAY = "org.springframework.cloud:spring-cloud-starter-gateway"

    const val SPRING_MAIL = "org.springframework.boot:spring-boot-starter-mail"

    const val LIQUIBASE = "org.liquibase:liquibase-core"
}
