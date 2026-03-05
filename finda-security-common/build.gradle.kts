description = "finda-security-common"

dependencies {
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:3.2.1")

    api("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")

    compileOnly("org.slf4j:slf4j-api:2.0.9")
}
