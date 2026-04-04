package finda.findabatch.adapter.out.grpc

import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class VolunteerGrpcClient {

    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("volunteer-service")
    private lateinit var volunteerServiceStub: VolunteerServiceGrpc.VolunteerServiceBlockingStub

    private val callTimeoutSeconds = 5L

    fun getAllRemindTimes(): List<RemindTimeItem> {
        return try {
            volunteerServiceStub
                .withDeadlineAfter(callTimeoutSeconds, TimeUnit.SECONDS)
                .getAllRemindTimes(Empty.getDefaultInstance())
                .itemsList
        } catch (e: StatusRuntimeException) {
            when (e.status.code) {
                Status.Code.DEADLINE_EXCEEDED -> {
                    log.error("gRPC getAllRemindTimes timeout")
                    throw e
                }
                else -> {
                    log.error("gRPC getAllRemindTimes failed", e)
                    throw e
                }
            }
        }
    }
}
