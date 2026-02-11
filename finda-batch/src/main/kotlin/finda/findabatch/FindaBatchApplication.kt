package finda.findabatch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
class FindaBatchApplication

fun main(args: Array<String>) {
    runApplication<FindaBatchApplication>(*args)
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}
