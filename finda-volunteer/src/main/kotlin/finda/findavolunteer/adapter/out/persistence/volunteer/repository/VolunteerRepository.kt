package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface VolunteerRepository : CrudRepository<VolunteerJpaEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<VolunteerJpaEntity>

    @Query(
        """
        select volunteer
        from VolunteerJpaEntity volunteer
        where (:status is null or volunteer.status = :status)
          and (:year is null or function('year', volunteer.workStartDate) = :year)
        order by volunteer.workStartDate desc
        """
    )
    fun findAllByStatusAndYearOrderByWorkStartDateDesc(
        @Param("status") status: VolunteerStatus?,
        @Param("year") year: Int?
    ): List<VolunteerJpaEntity>
}
