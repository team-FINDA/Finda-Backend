package finda.findavolunteer.adapter.out.grpc

import finda.findaauth.adapter.`in`.grpc.StudentServiceGrpc
import finda.findaauth.adapter.`in`.grpc.StudentsInfoResponse
import finda.findaauth.adapter.`in`.grpc.UserIdAndUserInfo
import finda.findaauth.adapter.`in`.grpc.UserListRequest
import finda.findaauth.adapter.`in`.grpc.UserRequest
import finda.findavolunteer.application.exception.grpc.StudentInfoNotFoundException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class StudentGrpcClient {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("auth-service")
    private lateinit var studentServiceStub: StudentServiceGrpc.StudentServiceBlockingStub

    private val callTimeoutSeconds = 2L

    /**
     * userId 목록으로 학생 정보 일괄 조회
     */
    fun getStudentsInfo(userIds: List<UUID>): StudentsInfoResponse {
        return try {
            studentServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getStudentsInfo(
                    UserListRequest.newBuilder()
                        .addAllUserIds(userIds.map { it.toString() })
                        .build()
                )
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.error("Students not found for ids=$userIds")
                    throw StudentInfoNotFoundException
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getStudentsInfo timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getStudentsInfo failed", e)
                    throw e
                }
            }
        }
    }

    /**
     * 단일 userId로 학생 정보 조회
     */
    fun getStudentInfo(userId: UUID): UserIdAndUserInfo? {
        return try {
            studentServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getStudentInfo(
                    UserRequest.newBuilder()
                        .setUserId(userId.toString())
                        .build()
                )
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.NOT_FOUND -> {
                    log.info("Student not found for id=$userId")
                    null
                }
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getStudentInfo timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getStudentInfo failed", e)
                    throw e
                }
            }
        }
    }
}
