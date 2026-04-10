package finda.findaauth.adapter.out.grpc

import finda.findaauth.adapter.`in`.grpc.GetTopActivitiesRequest
import finda.findaauth.adapter.`in`.grpc.VolunteerServiceGrpc
import finda.findaauth.application.port.out.grpc.VolunteerGrpcPort
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class VolunteerGrpcClient : VolunteerGrpcPort {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("volunteer-service")
    private lateinit var volunteerServiceStub: VolunteerServiceGrpc.VolunteerServiceBlockingStub

    private val callTimeoutSeconds = 2L

    override fun getTopActivities(userId: UUID): List<String> {
        return try {
            val request = GetTopActivitiesRequest.newBuilder()
                .setUserId(userId.toString())
                .build()

            volunteerServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getTopActivitiesByVolunteerTime(request)
                .activityNamesList
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("Volunteer activities not found for userId")
                    emptyList()
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getTopActivities timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getTopActivities failed", e)
                    throw e
                }
            }
        }
    }
}
