package finda.findanotification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
class FindaNotificationApplication

fun main(args: Array<String>) {
    runApplication<FindaNotificationApplication>(*args)
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}
