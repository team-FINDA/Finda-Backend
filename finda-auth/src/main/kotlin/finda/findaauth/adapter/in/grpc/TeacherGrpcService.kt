package finda.findaauth.adapter.`in`.grpc

import com.google.protobuf.Empty
import finda.findaauth.application.exception.teacher.TeacherNotFoundException
import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.port.out.teacher.TeacherQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.teacher.model.Teacher
import finda.findaauth.domain.user.model.User
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

@GrpcService
class TeacherGrpcService(
    private val teacherQueryPort: TeacherQueryPort,
    private val userQueryPort: UserQueryPort
) : TeacherServiceGrpc.TeacherServiceImplBase() {
    override fun getTeachersInfo(
        request: UserListRequest,
        responseObserver: StreamObserver<TeachersInfoResponse>
    ) = handleGrpc(responseObserver) {
        val teachersInfo = request.userIdsList
            .map(::parseUUID)
            .mapNotNull { userId ->
                val teacher = teacherQueryPort.findTeacherByUserId(userId) ?: return@mapNotNull null
                val user = userQueryPort.findById(userId) ?: return@mapNotNull null
                mapTeacherInfo(user, teacher)
            }

        TeachersInfoResponse.newBuilder()
            .addAllTeachersInfo(teachersInfo)
            .build()
    }

    override fun getTeacherInfo(
        request: UserRequest,
        responseObserver: StreamObserver<UserIdAndUserInfo>
    ) = handleGrpc(responseObserver) {
        val userId = parseUUID(request.userId)
        val user = userQueryPort.findById(userId)
            ?: throw UserNotFoundException
        val teacher = teacherQueryPort.findTeacherByUserId(userId)
            ?: throw TeacherNotFoundException

        mapTeacherInfo(user, teacher)
    }

    override fun getTeachersAllInfo(
        request: Empty,
        responseObserver: StreamObserver<TeachersInfoResponse>
    ) = handleGrpc(responseObserver) {
        val teachersInfo = teacherQueryPort.findAll()
            .mapNotNull { teacher ->
                val user = userQueryPort.findById(teacher.userId) ?: return@mapNotNull null
                mapTeacherInfo(user, teacher)
            }

        TeachersInfoResponse.newBuilder()
            .addAllTeachersInfo(teachersInfo)
            .build()
    }

    private fun mapTeacherInfo(user: User, teacher: Teacher): UserIdAndUserInfo {
        val authority = when (user.authority) {
            finda.security.passport.model.Authority.STUDENT -> Authority.STUDENT
            finda.security.passport.model.Authority.TEACHER -> Authority.TEACHER
        }

        return UserIdAndUserInfo.newBuilder()
            .setUserId(user.id.toString())
            .setUserInfo(
                UserInfo.newBuilder()
                    .setName(user.name)
                    .setEmail(user.email)
                    .setAuthority(authority)
                    .setTeacherInfo(
                        TeacherInfo.newBuilder()
                            .setTeacherId(teacher.id.toString())
                            .build()
                    )
                    .build()
            )
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

    private fun <T> handleGrpc(
        observer: StreamObserver<T>,
        block: () -> T
    ) {
        try {
            observer.onNext(block())
            observer.onCompleted()
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
