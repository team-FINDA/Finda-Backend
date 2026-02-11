object Dependencies {
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"
    const val JACKSON_TYPE = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${DependencyVersions.JACKSON_VERSION}"

    const val UUID_TIME = "com.fasterxml.uuid:java-uuid-generator:${DependencyVersions.UUID_TIME_VERSION}"

    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"

    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa:${PluginVersions.SPRING_PLUGIN_VERSION}"
    const val SPRING_JDBC = "org.springframework.boot:spring-boot-starter-jdbc"
    const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:${DependencyVersions.MYSQL}"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"

    const val KAFKA = "org.springframework.kafka:spring-kafka:${DependencyVersions.KAFKA}"
    const val QUARTZ = "org.springframework.boot:spring-boot-starter-quartz"
}
