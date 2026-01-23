package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceMonthJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRecurrenceMonthRepository : CrudRepository<ActivityRecurrenceMonthJpaEntity, UUID>
