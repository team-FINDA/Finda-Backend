package finda.findavolunteer.application.port.out.volunteer

import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import java.util.UUID

interface LoadVolunteerDocumentPort {
    fun loadVolunteerDocumentData(volunteerId: UUID): VolunteerDocumentData
}
