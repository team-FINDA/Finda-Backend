package finda.findavolunteer.adapter.out.persistence.studentparticitation.repository

import finda.findavolunteer.adapter.out.persistence.studentparticitation.entity.StudentParticipationJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StudentParticipationRepository : CrudRepository<StudentParticipationJpaEntity, UUID>
