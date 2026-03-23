package finda.findavolunteer.adapter.out.persistence.volunteer.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.VolunteerJpaEntity
import finda.findavolunteer.domain.volunteer.model.Volunteer
import org.springframework.stereotype.Component

@Component
class VolunteerMapper : GenericMapper<Volunteer, VolunteerJpaEntity> {

    override fun toDomain(entity: VolunteerJpaEntity): Volunteer {
        return Volunteer(
            id = entity.id!!,
            status = entity.status,
            personnel = entity.personnel,
            title = entity.title,
            unitVolunteerHours = entity.unitVolunteerHours,
            applicationStartDate = entity.applicationStartDate,
            applicationEndDate = entity.applicationEndDate,
            workStartDate = entity.workStartDate,
            workEndDate = entity.workEndDate,
            cycleType = entity.cycleType,
            userId = entity.userId,
            remindTime = entity.remindTime,
            description = entity.description,
            volunteerType = entity.volunteerType,
            groupVolunteerType = entity.groupVolunteerType,
        )
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
            remindTime = domain.remindTime,
<<<<<<< HEAD
<<<<<<< HEAD
            description = domain.description,
            volunteerType = domain.volunteerType,
            groupVolunteerType = domain.groupVolunteerType
=======
            description = domain.description
>>>>>>> origin/feat/(#33)-봉사-crud
=======
            description = domain.description
>>>>>>> origin/feat/(#33)-봉사-crud
        )
    }
}
