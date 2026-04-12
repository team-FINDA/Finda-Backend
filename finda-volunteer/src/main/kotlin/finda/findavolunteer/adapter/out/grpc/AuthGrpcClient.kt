package finda.findavolunteer.adapter.out.grpc

import finda.findaauth.adapter.`in`.grpc.AuthServiceGrpc
import finda.findaauth.adapter.`in`.grpc.UserRequest
import finda.findavolunteer.application.exception.grpc.UserNotFoundException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class AuthGrpcClient {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("auth-service")
    private lateinit var authServiceStub: AuthServiceGrpc.AuthServiceBlockingStub

    private val callTimeoutSeconds = 2L

    fun getUserName(userId: UUID): String {
        return try {
            authServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getUserName(
                    UserRequest.newBuilder()
                        .setUserId(userId.toString())
                        .build()
                ).userName
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("User not found")
                    throw UserNotFoundException
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getUserName timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getUserName failed", e)
                    throw e
                }
            }
        }
    }
}
