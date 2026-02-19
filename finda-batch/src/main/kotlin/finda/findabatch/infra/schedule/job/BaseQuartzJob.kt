package finda.findabatch.infra.schedule.job

import finda.findabatch.global.error.exception.BadJobDataException
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.slf4j.LoggerFactory

abstract class BaseQuartzJob : Job {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(context: JobExecutionContext) {
        try {
            doExecute(context)
        } catch (e: BadJobDataException) {
            log.error(
                "Bad job data: jobKey={}, triggerKey={}, fireTime={}, scheduledFireTime={}, jobDataMap={}, error={}",
                context.jobDetail.key,
                context.trigger.key,
                context.fireTime,
                context.scheduledFireTime,
                context.jobDetail.jobDataMap.wrappedMap,
                e.message
            )
            throw JobExecutionException(e, false)
        } catch (e: Exception) {
            log.error(
                "Job execution failed: jobKey={}, triggerKey={}, fireTime={}, error={}",
                context.jobDetail.key,
                context.trigger.key,
                context.fireTime,
                e.message,
                e
            )
            throw JobExecutionException(e, false)
        }
    }

    abstract fun doExecute(context: JobExecutionContext)
}
