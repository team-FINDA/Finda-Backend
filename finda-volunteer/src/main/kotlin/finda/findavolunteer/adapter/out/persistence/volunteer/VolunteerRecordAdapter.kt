package finda.findavolunteer.adapter.out.persistence.volunteer

import finda.findavolunteer.adapter.out.persistence.volunteer.mapper.VolunteerRecordMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRecordRepository
import finda.findavolunteer.application.exception.volunteer.VolunteerRecordNotFoundException
import finda.findavolunteer.application.port.out.volunteer.VolunteerRecordCommandPort
import finda.findavolunteer.application.port.out.volunteer.VolunteerRecordQueryPort
import finda.findavolunteer.domain.volunteer.model.VolunteerRecord
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class VolunteerRecordAdapter(
    private val volunteerRecordRepository: VolunteerRecordRepository,
    private val volunteerRecordMapper: VolunteerRecordMapper
) : VolunteerRecordCommandPort, VolunteerRecordQueryPort {

    override fun save(volunteerRecord: VolunteerRecord): VolunteerRecord {
        val entity = volunteerRecordRepository.save(volunteerRecordMapper.toEntity(volunteerRecord))
        return volunteerRecordMapper.toDomain(entity)
    }

    override fun findById(id: UUID): VolunteerRecord? {
        val entity = volunteerRecordRepository.findByIdOrNull(id)
        return entity?.let(volunteerRecordMapper::toDomain)
    }

    override fun findByUserId(userId: UUID): List<VolunteerRecord> =
        volunteerRecordRepository.findAllByUserId(userId)
            .map(volunteerRecordMapper::toDomain)

    override fun findByVolunteerId(volunteerId: UUID): List<VolunteerRecord> =
        volunteerRecordRepository.findAllByVolunteer_Id(volunteerId)
            .map(volunteerRecordMapper::toDomain)

    override fun findByIdOrThrow(id: UUID): VolunteerRecord =
        findById(id) ?: throw VolunteerRecordNotFoundException
}
