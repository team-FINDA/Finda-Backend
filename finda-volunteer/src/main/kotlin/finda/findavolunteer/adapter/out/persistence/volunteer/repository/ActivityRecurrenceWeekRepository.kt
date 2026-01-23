package finda.findavolunteer.adapter.out.persistence.volunteer.repository

import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceWeekJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ActivityRecurrenceWeekRepository : CrudRepository<ActivityRecurrenceWeekJpaEntity, UUID>
