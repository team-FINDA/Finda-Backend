package finda.findavolunteer.domain.volunteer.enum

enum class VolunteerSortBy {
    WORK_START_DATE;

    companion object {
        fun from(value: String?): VolunteerSortBy {
            if (value == null) return WORK_START_DATE

            return entries.firstOrNull { it.name == value.uppercase() }
                ?: throw IllegalArgumentException("Invalid volunteer sort by: $value")
        }
    }
}
