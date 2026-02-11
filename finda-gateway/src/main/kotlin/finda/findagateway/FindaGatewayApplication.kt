package finda.findagateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.util.TimeZone

@ConfigurationPropertiesScan
@SpringBootApplication
class FindaGatewayApplication

fun main(args: Array<String>) {
    runApplication<FindaGatewayApplication>(*args)
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}
