package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceYearJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRecurrenceYearRepository : CrudRepository<ActivityRecurrenceYearJpaEntity, UUID>
