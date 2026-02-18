package finda.findabatch.infra.schedule.config

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import javax.sql.DataSource

@Configuration
class QuartzConfig(
    private val dataSource: DataSource,
    private val applicationContext: ApplicationContext
) {

    @Bean
    fun schedulerFactoryBean(): SchedulerFactoryBean {
        val jobFactory = AutowiringSpringBeanJobFactory()
        jobFactory.setApplicationContext(applicationContext)

        val factory = SchedulerFactoryBean()
        factory.setJobFactory(jobFactory)
        factory.setDataSource(dataSource)
        factory.setApplicationContextSchedulerContextKey("applicationContext")
        factory.setOverwriteExistingJobs(true)
        factory.setWaitForJobsToCompleteOnShutdown(true)
        return factory
    }
}
