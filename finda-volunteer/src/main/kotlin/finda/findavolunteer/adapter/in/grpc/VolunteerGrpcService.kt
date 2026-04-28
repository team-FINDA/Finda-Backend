package finda.findavolunteer.adapter.`in`.grpc

import com.google.protobuf.Empty
import finda.findavolunteer.adapter.out.grpc.GetTopActivitiesRequest
import finda.findavolunteer.adapter.out.grpc.GetTopActivitiesResponse
import finda.findavolunteer.adapter.out.grpc.RemindTimeItem
import finda.findavolunteer.adapter.out.grpc.RemindTimeListResponse
import finda.findavolunteer.adapter.out.grpc.VolunteerServiceGrpc
import finda.findavolunteer.application.service.volunteer.GetVolunteerService
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory
import java.util.UUID

@GrpcService
class VolunteerGrpcService(
    private val getVolunteerService: GetVolunteerService
) : VolunteerServiceGrpc.VolunteerServiceImplBase() {

    companion object {
        private val log = LoggerFactory.getLogger(VolunteerGrpcService::class.java)
    }

    override fun getAllRemindTimes(
        request: Empty,
        responseObserver: StreamObserver<RemindTimeListResponse>
    ) = handleGrpc(responseObserver) {
        val items = getVolunteerService.getAllRemindTimes()
            .map { volunteer ->
                RemindTimeItem.newBuilder()
                    .setVolunteerId(volunteer.id.toString())
                    .setRemindTime(volunteer.remindTime.toString())
                    .build()
            }
        RemindTimeListResponse.newBuilder()
            .addAllItems(items)
            .build()
    }

    override fun getTopActivitiesByVolunteerTime(
        request: GetTopActivitiesRequest,
        responseObserver: StreamObserver<GetTopActivitiesResponse>
    ) = handleGrpc(responseObserver) {
        val activities = getVolunteerService.getTopActivitiesByVolunteerTime(
            parseUUID(request.userId)
        )

        GetTopActivitiesResponse.newBuilder()
            .addAllActivityNames(activities)
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
            log.error("gRPC internal error", e)
            observer.onError(
                Status.INTERNAL
                    .withDescription("Internal server error")
                    .withCause(e)
                    .asRuntimeException()
            )
        }
    }
}
