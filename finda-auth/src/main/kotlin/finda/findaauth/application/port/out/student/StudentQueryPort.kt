package finda.findaauth.application.port.out.student

import java.util.UUID

interface StudentQueryPort {
    fun existsByUserId(userId: UUID): Boolean
}
