package finda.findanotification.adapter.out.grpc

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

/**
 * Auth 서버에서 deviceToken을 조회하는 gRPC Client
 */
@Component
class AuthGrpcClient {
    @GrpcClient("auth-service")
    private lateinit var authServiceStub: AuthServiceGrpc.AuthServiceBlockingStub

    fun getDeviceToken(userId: String): String {
        val response = authServiceStub.getDeviceToken(
            UserRequest.newBuilder()
                .setUserId(userId)
                .build()
        )
        return response.deviceToken
    }

    fun getDeviceTokens(userIds: List<String>): List<String> {
        val response = authServiceStub.getDeviceTokens(
            UserListRequest.newBuilder()
                .addAllUserIds(userIds)
                .build()
        )
        return response.tokensList.map { it.deviceToken }
    }
}
