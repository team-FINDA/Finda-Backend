package finda.findaauth.adapter.out.persistence.teacher.repository

import finda.findaauth.adapter.out.persistence.teacher.entity.TeacherJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TeacherRepository : CrudRepository<TeacherJpaEntity, UUID> {
    fun existsByUserId(userId: UUID): Boolean
}
