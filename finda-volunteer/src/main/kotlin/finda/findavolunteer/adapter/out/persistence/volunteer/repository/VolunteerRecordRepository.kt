package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerRecordJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerRecordRepository : CrudRepository<VolunteerRecordJpaEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<VolunteerRecordJpaEntity>
    fun findAllByVolunteer_Id(volunteerId: UUID): List<VolunteerRecordJpaEntity>
}
