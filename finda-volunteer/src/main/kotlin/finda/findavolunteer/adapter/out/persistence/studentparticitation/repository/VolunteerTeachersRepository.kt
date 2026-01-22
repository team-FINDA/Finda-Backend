package finda.findavolunteer.adapter.out.persistence.studentparticitation.repository

import finda.findavolunteer.adapter.out.persistence.studentparticitation.entity.VolunteerTeachersJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerTeachersRepository : CrudRepository<VolunteerTeachersJpaEntity, UUID>
