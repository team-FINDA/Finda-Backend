package finda.findabatch.infra.schedule.job

import finda.findabatch.global.error.exception.BadJobDataException
import org.quartz.JobExecutionContext
import java.util.UUID

fun JobExecutionContext.requireString(key: String): String {
    val value = jobDetail.jobDataMap.getString(key)
    if (value.isNullOrBlank()) throw BadJobDataException("Missing or blank jobDataMap key: '$key'")
    return value
}

fun JobExecutionContext.requireUuid(key: String): UUID {
    val value = requireString(key)
    return try {
        UUID.fromString(value)
    } catch (e: IllegalArgumentException) {
        throw BadJobDataException("Invalid UUID format for key '$key': $value")
    }
}

inline fun <reified T : Enum<T>> JobExecutionContext.requireEnum(key: String): T {
    val value = requireString(key)
    return try {
        enumValueOf<T>(value)
    } catch (e: IllegalArgumentException) {
        throw BadJobDataException("Invalid ${T::class.simpleName} value for key '$key': $value")
    }
}
