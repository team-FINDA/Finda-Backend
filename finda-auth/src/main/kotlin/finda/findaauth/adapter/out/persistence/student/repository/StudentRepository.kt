package finda.findaauth.adapter.out.persistence.student.repository

import finda.findaauth.adapter.out.persistence.student.entity.StudentJpaEntity
import finda.findaauth.adapter.out.persistence.user.entity.UserJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StudentRepository : CrudRepository<StudentJpaEntity, UUID> {
    fun existsByUser(user: UserJpaEntity): Boolean

    fun existsByUserId(userId: UUID): Boolean
}
