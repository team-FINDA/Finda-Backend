package finda.findaauth.adapter.`in`.grpc

import finda.findaauth.application.exception.devicetoken.DeviceTokenNotFoundException
import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.service.devicetoken.GetDeviceTokenService
import finda.findaauth.application.service.user.GetUserService
import finda.findaauth.domain.devicetoken.model.DeviceToken
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

/**
 * RestController와 유사한 개념
 */
@GrpcService
class AuthGrpcService(
    private val getDeviceTokenService: GetDeviceTokenService,
    private val getUserService: GetUserService
) : AuthServiceGrpc.AuthServiceImplBase() {

    override fun getDeviceToken(
        request: UserRequest,
        responseObserver: StreamObserver<DeviceTokenResponse>
    ) = handleGrpc(responseObserver) {
        val userId = parseUUID(request.userId)
        val token = getDeviceTokenService.getByUserId(userId)
        mapToken(token)
    }

    override fun getDeviceTokens(
        request: UserListRequest,
        responseObserver: StreamObserver<DeviceTokenListResponse>
    ) = handleGrpc(responseObserver) {
        val userIds = request.userIdsList.map(::parseUUID)
        val tokens = getDeviceTokenService.getAllByUserIds(userIds)

        DeviceTokenListResponse.newBuilder()
            .addAllTokens(tokens.map(::mapToken))
            .build()
    }

    override fun getUserName(
        request: UserRequest,
        responseObserver: StreamObserver<UserNameResponse>
    ) = handleGrpc(responseObserver) {
        val userId = parseUUID(request.userId)
        val user = getUserService.getById(userId)

        UserNameResponse.newBuilder()
            .setUserName(user.name)
            .build()
    }

    private fun parseUUID(value: String): UUID =
        try {
            UUID.fromString(value)
        } catch (e: IllegalArgumentException) {
            throw Status.INVALID_ARGUMENT
                .withDescription("Invalid UUID format")
                .withCause(e)
                .asRuntimeException()
        }

    /**
     * domain을 gRPC Response로 변환
     */
    private fun mapToken(token: DeviceToken) =
        DeviceTokenResponse.newBuilder()
            .setDeviceToken(token.deviceToken)
            .setOs(token.os.name)
            .build()

    /**
     * gRPC 요청 처리 메서드
     */
    private fun <T> handleGrpc(
        observer: StreamObserver<T>,
        block: () -> T
    ) {
        try {
            observer.onNext(block())
            observer.onCompleted()
        } catch (e: UserNotFoundException) {
            observer.onError(
                Status.NOT_FOUND
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        } catch (e: DeviceTokenNotFoundException) {
            observer.onError(
                Status.NOT_FOUND
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        } catch (e: StatusRuntimeException) {
            observer.onError(e)
        } catch (e: Exception) {
            observer.onError(
                Status.INTERNAL
                    .withDescription("Internal server error")
                    .withCause(e)
                    .asRuntimeException()
            )
        }
    }
}
