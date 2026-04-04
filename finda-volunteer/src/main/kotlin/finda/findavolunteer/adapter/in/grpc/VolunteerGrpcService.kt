package finda.findavolunteer.adapter.`in`.grpc

import com.google.protobuf.Empty
import finda.findavolunteer.adapter.out.grpc.RemindTimeItem
import finda.findavolunteer.adapter.out.grpc.RemindTimeListResponse
import finda.findavolunteer.adapter.out.grpc.VolunteerServiceGrpc
import finda.findavolunteer.application.service.volunteer.GetVolunteerService
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class VolunteerGrpcService(
    private val getVolunteerService: GetVolunteerService
) : VolunteerServiceGrpc.VolunteerServiceImplBase() {

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
