package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.volunteer.model.Volunteer
import org.springframework.stereotype.Component

@Component
class VolunteerMapper : GenericMapper<Volunteer, VolunteerJpaEntity> {

    override fun toDomain(entity: VolunteerJpaEntity?): Volunteer? {
        return entity?.let {
            Volunteer(
                id = it.id!!,
                status = it.status,
                personnel = it.personnel,
                title = it.title,
                unitVolunteerHours = it.unitVolunteerHours,
                applicationStartDate = it.applicationStartDate,
                applicationEndDate = it.applicationEndDate,
                workStartDate = it.workStartDate,
                workEndDate = it.workEndDate,
                cycleType = it.cycleType,
                userId = it.userId,
                remindTime = it.remindTime
            )
        }
    }

    override fun toEntity(domain: Volunteer): VolunteerJpaEntity {
        return VolunteerJpaEntity(
            id = domain.id,
            status = domain.status,
            personnel = domain.personnel,
            title = domain.title,
            unitVolunteerHours = domain.unitVolunteerHours,
            applicationStartDate = domain.applicationStartDate,
            applicationEndDate = domain.applicationEndDate,
            workStartDate = domain.workStartDate,
            workEndDate = domain.workEndDate,
            cycleType = domain.cycleType,
            userId = domain.userId,
            remindTime = domain.remindTime
        )
    }
}
