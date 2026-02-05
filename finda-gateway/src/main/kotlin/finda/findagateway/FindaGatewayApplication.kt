package finda.findagateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class FindaGatewayApplication

fun main(args: Array<String>) {
    runApplication<FindaGatewayApplication>(*args)
}
