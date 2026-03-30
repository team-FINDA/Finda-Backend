package finda.findavolunteer.adapter.out.persistence.participation.repository

import finda.findavolunteer.adapter.out.persistence.participation.entity.TeacherParticipationJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TeacherParticipationRepository : CrudRepository<TeacherParticipationJpaEntity, UUID> {
    fun findByUserId(userId: UUID): TeacherParticipationJpaEntity?
}
