package finda.findavolunteer.adapter.out.persistence.volunteer.entity

import finda.findavolunteer.adapter.out.persistence.BaseEntity
import finda.findavolunteer.adapter.out.persistence.activity.entity.ActivityJpaEntity
import finda.findavolunteer.adapter.out.persistence.participation.entity.StudentParticipationJpaEntity
import finda.findavolunteer.adapter.out.persistence.participation.entity.TeacherParticipationJpaEntity
import finda.findavolunteer.adapter.out.persistence.qrcode.entity.QrCodeJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceMonthJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.entity.recurrence.ActivityRecurrenceWeekJpaEntity
import finda.findavolunteer.domain.volunteer.enum.CycleType
import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.domain.volunteer.enum.VolunteerStatus
import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "tbl_volunteer")
class VolunteerJpaEntity(
    id: UUID?,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: VolunteerStatus,

    @Column(name = "personnel", nullable = false)
    val personnel: Int,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "unit_volunteer_hours", nullable = false)
    val unitVolunteerHours: Float,

    @Column(name = "application_start_date", nullable = false)
    val applicationStartDate: LocalDate,

    @Column(name = "application_end_date", nullable = false)
    val applicationEndDate: LocalDate,

    @Column(name = "work_start_date", nullable = false)
    val workStartDate: LocalDate,

    @Column(name = "work_end_date", nullable = false)
    val workEndDate: LocalDate,

    @Column(name = "cycle_type", nullable = true)
    @Enumerated(EnumType.STRING)
    val cycleType: CycleType?,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "remind_time", nullable = false)
    val remindTime: LocalTime,

    @Column(name = "group_volunteer_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val groupVolunteerType: GroupVolunteerType,

    @Column(name = "volunteer_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val volunteerType: VolunteerType,

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val activities: List<ActivityJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val schedules: List<VolunteerScheduleJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val records: List<VolunteerRecordJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val studentParticipations: List<StudentParticipationJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val teacherParticipations: List<TeacherParticipationJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val qrCodes: List<QrCodeJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val recurrenceMonths: List<ActivityRecurrenceMonthJpaEntity> = emptyList(),

    @OneToMany(mappedBy = "volunteer", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val recurrenceWeeks: List<ActivityRecurrenceWeekJpaEntity> = emptyList()
) : BaseEntity(id)
