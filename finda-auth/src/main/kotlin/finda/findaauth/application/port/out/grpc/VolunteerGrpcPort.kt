package finda.findaauth.application.port.out.grpc

import java.util.UUID

interface VolunteerGrpcPort {
    fun getTopActivities(id: UUID): List<String>
}
