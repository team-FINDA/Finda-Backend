package finda.findabatch.global.error.exception

import java.util.UUID

class RemindTimeNotFoundException(volunteerId: UUID) : RuntimeException(
    "remind_time not found for volunteer: $volunteerId. " +
        "Volunteer CDC event may not have arrived yet."
)
