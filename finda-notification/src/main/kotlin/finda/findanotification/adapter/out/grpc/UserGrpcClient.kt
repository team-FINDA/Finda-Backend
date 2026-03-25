package finda.findanotification.adapter.out.grpc

import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * Auth 서버에서 user 정보를 조회하는 gRPC Client
 */
@Component
class UserGrpcClient {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("auth-service")
    private lateinit var authServiceStub: AuthServiceGrpc.AuthServiceBlockingStub

    private val callTimeoutSeconds = 2L

    /**
     * userId로 userName 조회
     */
    fun getUserName(userId: UUID): String? {
        return try {
            val response = authServiceStub
                .withDeadlineAfter(callTimeoutSeconds, java.util.concurrent.TimeUnit.SECONDS)
                .getUserName(
                    UserRequest.newBuilder()
                        .setUserId(userId.toString())
                        .build()
                )
            response.userName
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("User not found for id=$userId")
                    null
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
