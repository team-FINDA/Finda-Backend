package finda.findavolunteer.adapter.out.persistence.studentparticipation.repository

import finda.findavolunteer.adapter.out.persistence.studentparticipation.entity.VolunteerTeachersJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerTeachersRepository : CrudRepository<VolunteerTeachersJpaEntity, UUID>
