package finda.findavolunteer.application.port.out.activity

import finda.findavolunteer.domain.activity.model.UserActivity

interface UserActivityCommandPort {
    fun save(userActivity: UserActivity): UserActivity
}
