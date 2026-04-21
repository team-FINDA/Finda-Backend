package finda.findavolunteer.adapter.out.grpc

import finda.findaauth.adapter.`in`.grpc.AddVolunteerTimeRequest
import finda.findaauth.adapter.`in`.grpc.StudentServiceGrpc
import finda.findavolunteer.application.port.out.user.UserCommandPort
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class UserGrpcCommandAdapter : UserCommandPort {
    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("auth-service")
    private lateinit var studentServiceStub: StudentServiceGrpc.StudentServiceBlockingStub

    override fun addVolunteerTime(userId: UUID, volunteerTime: Float) {
        try {
            studentServiceStub
                .withDeadlineAfter(2L, TimeUnit.SECONDS)
                .addVolunteerTime(
                    AddVolunteerTimeRequest.newBuilder()
                        .setUserId(userId.toString())
                        .setVolunteerTime(volunteerTime)
                        .build()
                )
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> throw e
                else -> {
                    log.error("gRPC addVolunteerTime failed.", e)
                    throw e
                }
            }
        }
    }
}
