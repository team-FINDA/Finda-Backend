package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
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
    fun findAllByRemindTimeIsNotNull(): List<VolunteerJpaEntity>

    // TODO: JPQL은 LIMIT을 공식 지원하지 않음. QueryDSL 전환 시 수정 필요
    @Query(
        """
    SELECT v.title
    FROM VolunteerJpaEntity v
    JOIN StudentParticipationJpaEntity sp ON sp.volunteer = v
    WHERE sp.userId = :userId
    AND sp.status = 'PARTICIPATED'
    GROUP BY v.id, v.title
    ORDER BY SUM(v.unitVolunteerHours) DESC
    LIMIT 3
"""
    )
    fun findTop3TitlesByUserId(@Param("userId") userId: UUID): List<String>
}
