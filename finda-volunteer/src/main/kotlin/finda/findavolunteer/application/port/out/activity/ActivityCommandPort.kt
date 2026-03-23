package finda.findavolunteer.application.port.out.activity

import finda.findavolunteer.domain.activity.model.Activity

interface ActivityCommandPort {
    fun save(activity: Activity): Activity
}
