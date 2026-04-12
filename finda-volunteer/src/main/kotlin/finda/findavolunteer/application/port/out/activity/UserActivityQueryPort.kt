package finda.findavolunteer.application.port.out.activity

import finda.findavolunteer.domain.activity.model.UserActivity
import java.util.UUID

interface UserActivityQueryPort {
    fun findById(id: UUID): UserActivity?
}
