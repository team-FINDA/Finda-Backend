package finda.findavolunteer.application.service.volunteer

import finda.findavolunteer.application.exception.volunteer.InvalidVolunteerSortByException
import finda.findavolunteer.application.port.`in`.volunteer.VolunteerListUseCase
import finda.findavolunteer.application.port.`in`.volunteer.dto.response.VolunteerListResult
import finda.findavolunteer.application.port.out.volunteer.VolunteerListQueryPort
import finda.findavolunteer.domain.volunteer.enum.VolunteerSortBy
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import org.springframework.stereotype.Service

@Service
class VolunteerListService(
    private val volunteerListQueryPort: VolunteerListQueryPort
) : VolunteerListUseCase {
    override fun execute(
        status: VolunteerStatus?,
        year: Int?,
        sortBy: String?
    ): List<VolunteerListResult> {
        val sortType = try {
            VolunteerSortBy.from(sortBy)
        } catch (_: IllegalArgumentException) {
            throw InvalidVolunteerSortByException
        }

        return volunteerListQueryPort.findAll(
            status = status,
            year = year,
            sortBy = sortType
        ).map {
            VolunteerListResult(
                volunteerId = it.id,
                title = it.title,
                workStartDate = it.workStartDate,
                workEndDate = it.workEndDate,
                unitVolunteerHours = it.unitVolunteerHours
            )
        }
    }
}
