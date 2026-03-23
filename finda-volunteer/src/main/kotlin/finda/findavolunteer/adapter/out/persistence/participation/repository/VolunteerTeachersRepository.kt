package finda.findavolunteer.adapter.out.persistence.participation.repository

import finda.findavolunteer.adapter.out.persistence.participation.entity.VolunteerTeachersJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerTeachersRepository : CrudRepository<VolunteerTeachersJpaEntity, UUID>
