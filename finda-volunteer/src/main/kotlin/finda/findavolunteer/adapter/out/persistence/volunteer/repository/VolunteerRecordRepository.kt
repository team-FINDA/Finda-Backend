package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerRecordJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerRecordRepository : CrudRepository<VolunteerRecordJpaEntity, UUID>
