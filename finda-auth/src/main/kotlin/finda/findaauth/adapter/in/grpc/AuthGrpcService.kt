package finda.findaauth.adapter.`in`.grpc

import finda.findaauth.application.service.devicetoken.GetDeviceTokenService
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

@GrpcService
class AuthGrpcService(
    private val getDeviceTokenService: GetDeviceTokenService
) : AuthServiceGrpc.AuthServiceImplBase() {

    override fun getDeviceToken(
        request: UserRequest,
        responseObserver: StreamObserver<DeviceTokenResponse>
    ) {
        val token = getDeviceTokenService.getByUserId(UUID.fromString(request.userId))
        responseObserver.onNext(
            DeviceTokenResponse.newBuilder()
                .setDeviceToken(token.deviceToken)
                .setOs(token.os.name)
                .build()
        )
        responseObserver.onCompleted()
    }

    override fun getDeviceTokens(
        request: UserListRequest,
        responseObserver: StreamObserver<DeviceTokenListResponse>
    ) {
        val tokens = getDeviceTokenService.getAllByUserIds(
            request.userIdsList.map { UUID.fromString(it) }
        )
        responseObserver.onNext(
            DeviceTokenListResponse.newBuilder()
                .addAllTokens(
                    tokens.map {
                        DeviceTokenResponse.newBuilder()
                            .setDeviceToken(it.deviceToken)
                            .setOs(it.os.name)
                            .build()
                    }
                )
                .build()
        )
        responseObserver.onCompleted()
    }
}
