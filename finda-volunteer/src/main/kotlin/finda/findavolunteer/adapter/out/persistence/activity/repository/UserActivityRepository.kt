package finda.findavolunteer.adapter.out.persistence.activity.repository

import finda.findavolunteer.adapter.out.persistence.activity.entity.UserActivityJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserActivityRepository : CrudRepository<UserActivityJpaEntity, UUID> {
    @Query(
        """
        select userActivity
        from UserActivityJpaEntity userActivity
        left join fetch userActivity.activity
        where userActivity.id = :id
        """
    )
    fun findWithActivityById(@Param("id") id: UUID): UserActivityJpaEntity?
}
