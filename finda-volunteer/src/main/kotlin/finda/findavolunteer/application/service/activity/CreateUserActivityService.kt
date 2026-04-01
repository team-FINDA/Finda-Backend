package finda.findavolunteer.application.service.activity

import finda.findavolunteer.adapter.`in`.activity.dto.request.CreateUserActivityRequest
import finda.findavolunteer.application.port.`in`.activity.CreateUserActivityUseCase
import finda.findavolunteer.application.port.out.activity.ActivityQueryPort
import finda.findavolunteer.application.port.out.activity.UserActivityCommandPort
import finda.findavolunteer.domain.activity.model.UserActivity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateUserActivityService(
    val userActivityCommandPort: UserActivityCommandPort,
    val activityQueryPort: ActivityQueryPort
) : CreateUserActivityUseCase {
    @Transactional
    override fun execute(request: CreateUserActivityRequest) {
        request.userActivityList.forEach {
            val activity = activityQueryPort.findById(it.activityId)
            userActivityCommandPort.save(
                UserActivity(
                    activityId = activity!!.id,
                    userId = it.userId
                )
            )
        }
    }
}
