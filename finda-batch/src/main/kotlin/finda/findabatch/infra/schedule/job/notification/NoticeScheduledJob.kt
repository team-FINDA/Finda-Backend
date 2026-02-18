package finda.findabatch.infra.schedule.job.notification

import finda.findabatch.infra.event.dto.notification.NoticeScheduledFiredEvent
import finda.findabatch.infra.event.producer.NoticeScheduledEventProducer
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NoticeScheduledJob(
    private val noticeScheduledEventProducer: NoticeScheduledEventProducer
) : Job {

    override fun execute(context: JobExecutionContext) {
        val noticeId = context.jobDetail.jobDataMap.getString("noticeId")

        noticeScheduledEventProducer.produce(
            NoticeScheduledFiredEvent(
                noticeId = UUID.fromString(noticeId)
            )
        )
    }
}
