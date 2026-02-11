package finda.findavolunteer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.TimeZone

@SpringBootApplication
@EnableJpaAuditing
class FindaVolunteerApplication

fun main(args: Array<String>) {
    runApplication<FindaVolunteerApplication>(*args)
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
}
