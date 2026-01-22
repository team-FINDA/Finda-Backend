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

    override fun toDomain(entity: VolunteerRecordJpaEntity?): VolunteerRecord? {
        return entity?.let {
            VolunteerRecord(
                id = it.id!!,
                userId = it.userId,
                time = it.time,
                title = it.title,
                volunteerId = it.volunteer!!.id!!
            )
        }
    }

    override fun toEntity(domain: VolunteerRecord): VolunteerRecordJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return VolunteerRecordJpaEntity(
            id = domain.id,
            userId = domain.userId,
            time = domain.time,
            title = domain.title,
            volunteer = volunteer
        )
    }
}
