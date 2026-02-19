package finda.findabatch.infra.schedule.job.notification

import finda.findabatch.infra.event.dto.notification.NoticeScheduledFiredEvent
import finda.findabatch.infra.event.producer.NoticeScheduledEventProducer
import finda.findabatch.infra.schedule.job.BaseQuartzJob
import finda.findabatch.infra.schedule.job.requireUuid
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class NoticeScheduledJob(
    private val noticeScheduledEventProducer: NoticeScheduledEventProducer
) : BaseQuartzJob() {

    override fun doExecute(context: JobExecutionContext) {
        val noticeId = context.requireUuid("noticeId")

        noticeScheduledEventProducer.produce(
            NoticeScheduledFiredEvent(noticeId = noticeId)
        )
    }
}
