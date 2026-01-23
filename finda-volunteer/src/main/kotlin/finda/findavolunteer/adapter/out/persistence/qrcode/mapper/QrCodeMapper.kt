package finda.findavolunteer.adapter.out.persistence.qrcode.mapper

import finda.findavolunteer.adapter.out.persistence.GenericMapper
import finda.findavolunteer.adapter.out.persistence.qrcode.entity.QrCodeJpaEntity
import finda.findavolunteer.adapter.out.persistence.volunteer.repository.VolunteerRepository
import finda.findavolunteer.domain.qrcode.model.QrCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class QrCodeMapper(
    private val volunteerRepository: VolunteerRepository
) : GenericMapper<QrCode, QrCodeJpaEntity> {

    override fun toDomain(entity: QrCodeJpaEntity?): QrCode? {
        return entity?.let {
            QrCode(
                id = it.id!!,
                volunteerId = it.volunteer!!.id!!,
                code = it.code,
                generatedAt = it.generatedAt,
                isUsed = it.isUsed,
                usedAt = it.usedAt,
                studentId = it.studentId,
                teacherId = it.teacherId
            )
        }
    }

    override fun toEntity(domain: QrCode): QrCodeJpaEntity {
        val volunteer = volunteerRepository.findByIdOrNull(domain.volunteerId)

        return QrCodeJpaEntity(
            id = domain.id,
            volunteer = volunteer,
            code = domain.code,
            generatedAt = domain.generatedAt,
            isUsed = domain.isUsed,
            usedAt = domain.usedAt,
            studentId = domain.studentId,
            teacherId = domain.teacherId
        )
    }
}
