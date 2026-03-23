package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerRecordJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VolunteerRecordMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<VolunteerRecord, VolunteerRecordJpaEntity> {

    override fun toDomain(entity: VolunteerRecordJpaEntity): VolunteerRecord {
        return VolunteerRecord(
            id = entity.id!!,
            userId = entity.userId,
            volunteerTime = entity.volunteerTime,
            title = entity.title,
            volunteerId = entity.volunteer!!.id!!
        )
    }

    override fun toEntity(domain: VolunteerRecord): VolunteerRecordJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return VolunteerRecordJpaEntity(
            id = domain.id,
            userId = domain.userId,
            volunteerTime = domain.volunteerTime,
            title = domain.title,
            volunteer = volunteer
        )
    }
}
