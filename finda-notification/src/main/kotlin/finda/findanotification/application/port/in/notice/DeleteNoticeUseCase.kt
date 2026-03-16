package finda.findanotification.application.port.`in`.notice

import java.util.UUID

interface DeleteNoticeUseCase {
    fun execute(id: UUID)
}
