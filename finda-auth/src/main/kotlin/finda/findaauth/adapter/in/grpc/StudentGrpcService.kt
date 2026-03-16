package finda.findaauth.adapter.`in`.grpc

import com.google.protobuf.Empty
import finda.findaauth.application.exception.student.StudentNotFoundException
import finda.findaauth.application.exception.user.UserNotFoundException
import finda.findaauth.application.port.out.student.StudentQueryPort
import finda.findaauth.application.port.out.user.UserQueryPort
import finda.findaauth.domain.student.model.Student
import finda.findaauth.domain.user.model.User
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID

@GrpcService
class StudentGrpcService(
    private val studentQueryPort: StudentQueryPort,
    private val userQueryPort: UserQueryPort
) : StudentServiceGrpc.StudentServiceImplBase() {
    override fun getStudentsInfo(
        request: UserListRequest,
        responseObserver: StreamObserver<StudentsInfoResponse>
    ) = handleGrpc(responseObserver) {
        val studentsInfo = request.userIdsList
            .map(::parseUUID)
            .map { userId ->
                val student = studentQueryPort.findStudentByUserId(userId)
                    ?: throw StudentNotFoundException
                val user = userQueryPort.findById(userId)
                    ?: throw UserNotFoundException
                mapStudentInfo(user, student)
            }

        StudentsInfoResponse.newBuilder()
            .addAllStudentsInfo(studentsInfo)
            .build()
    }

    override fun getStudentInfo(
        request: UserRequest,
        responseObserver: StreamObserver<UserIdAndUserInfo>
    ) = handleGrpc(responseObserver) {
        val userId = parseUUID(request.userId)
        val student = studentQueryPort.findStudentByUserId(userId)
            ?: throw StudentNotFoundException
        val user = userQueryPort.findById(userId)
            ?: throw UserNotFoundException

        mapStudentInfo(user, student)
    }

    override fun getStudentsAllInfo(
        request: Empty,
        responseObserver: StreamObserver<StudentsInfoResponse>
    ) = handleGrpc(responseObserver) {
        val studentsInfo = studentQueryPort.findAll()
            .mapNotNull { student ->
                val user = userQueryPort.findById(student.userId) ?: return@mapNotNull null
                mapStudentInfo(user, student)
            }

        StudentsInfoResponse.newBuilder()
            .addAllStudentsInfo(studentsInfo)
            .build()
    }

    private fun mapStudentInfo(user: User, student: Student): UserIdAndUserInfo {
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
                    .setGrade(student.grade)
                    .setClassNum(student.classNum)
                    .setNum(student.num)
                    .setTotalVolunteerTime(student.totalVolunteerTime)
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
