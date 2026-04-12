package finda.findavolunteer.adapter.out.persistence.activity

import finda.findavolunteer.adapter.out.persistence.activity.mapper.UserActivityMapper
import finda.findavolunteer.adapter.out.persistence.activity.repository.UserActivityRepository
import finda.findavolunteer.application.port.out.activity.UserActivityCommandPort
import finda.findavolunteer.application.port.out.activity.UserActivityQueryPort
import finda.findavolunteer.domain.activity.model.UserActivity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserActivityAdapter(
    private val userActivityRepository: UserActivityRepository,
    private val userActivityMapper: UserActivityMapper
) : UserActivityCommandPort, UserActivityQueryPort {
    override fun save(userActivity: UserActivity): UserActivity {
        val entity = userActivityRepository.save(userActivityMapper.toEntity(userActivity))
        return userActivityMapper.toDomain(entity)
    }

    override fun findById(id: UUID): UserActivity? {
        val entity = userActivityRepository.findWithActivityById(id)
        return entity?.let(userActivityMapper::toDomain)
    }
}
