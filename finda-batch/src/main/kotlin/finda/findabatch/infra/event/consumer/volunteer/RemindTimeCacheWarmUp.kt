package finda.findabatch.infra.event.consumer.volunteer

import finda.findabatch.adapter.out.grpc.VolunteerGrpcClient
import finda.findabatch.infra.event.dto.volunteer.toLocalTime
import finda.findabatch.infra.schedule.job.volunteer.VolunteerRemindJobScheduler
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

/**
 * 애플리케이션 시작 시 Volunteer 모듈에서 remind_time을 조회하여 remindTimeCache를 초기화
 *
 * 재시작 시 메모리 캐시가 초기화되므로 tbl_volunteer_schedule CDC 이벤트가
 * 도착하기 전에 캐시를 미리 채워 알림 잡 등록 누락을 방지함
 */
@Component
class RemindTimeCacheWarmUp(
    private val volunteerRemindJobScheduler: VolunteerRemindJobScheduler,
    private val volunteerGrpcClient: VolunteerGrpcClient
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments) {
        try {
            volunteerGrpcClient.getAllRemindTimes()
                .forEach {
                    volunteerRemindJobScheduler.remindTimeCache[UUID.fromString(it.volunteerId)] =
                        Optional.of(it.remindTime.toLocalTime())
                }
            log.info("remindTimeCache warm-up success: ${volunteerRemindJobScheduler.remindTimeCache.size}")
        } catch (e: Exception) {
            log.error("remindTimeCache warm-up failed", e)
        }
    }
}
