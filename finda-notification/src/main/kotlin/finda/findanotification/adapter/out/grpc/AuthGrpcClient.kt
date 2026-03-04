package finda.findanotification.adapter.out.grpc

import finda.findanotification.application.port.`in`.devicetoken.DeviceTokenInfo
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * Auth 서버에서 deviceToken을 조회하는 gRPC Client
 */
@Component
class AuthGrpcClient {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("auth-service")
    private lateinit var authServiceStub: AuthServiceGrpc.AuthServiceBlockingStub

    private val callTimeoutSeconds = 2L

    fun getDeviceToken(userId: UUID): DeviceTokenInfo? {
        return try {
            val response = authServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getDeviceToken(
                    UserRequest.newBuilder()
                        .setUserId(userId.toString())
                        .build()
                )

            DeviceTokenInfo(
                token = response.deviceToken,
                os = response.os
            )
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("DeviceToken not found")
                    null
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getDeviceToken timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getDeviceToken failed", e)
                    throw e
                }
            }
        }
    }

    fun getDeviceTokens(userIds: List<UUID>): List<DeviceTokenInfo> {
        if (userIds.isEmpty()) return emptyList()

        return try {
            val response = authServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getDeviceTokens(
                    UserListRequest.newBuilder()
                        .addAllUserIds(userIds.map { it.toString() })
                        .build()
                )

            response.tokensList.map {
                DeviceTokenInfo(
                    token = it.deviceToken,
                    os = it.os
                )
            }
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("DeviceTokens not found")
                    emptyList()
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getDeviceTokens timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getDeviceTokens failed", e)
                    throw e
                }
            }
        }
    }
}
