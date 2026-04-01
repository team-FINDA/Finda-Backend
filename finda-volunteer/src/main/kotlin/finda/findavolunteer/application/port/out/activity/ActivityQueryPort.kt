package finda.findavolunteer.application.port.out.activity

import finda.findavolunteer.domain.activity.model.Activity
import java.util.UUID

interface ActivityQueryPort {
    fun findById(id: UUID): Activity?
}
