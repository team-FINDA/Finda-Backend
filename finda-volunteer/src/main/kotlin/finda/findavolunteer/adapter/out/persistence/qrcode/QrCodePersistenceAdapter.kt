package finda.findavolunteer.adapter.out.persistence.qrcode

import finda.findavolunteer.adapter.out.persistence.qrcode.mapper.QrCodeMapper
import finda.findavolunteer.adapter.out.persistence.qrcode.repository.QrCodeRepository
import finda.findavolunteer.application.port.out.qrcode.QrCodeCommandPort
import finda.findavolunteer.application.port.out.qrcode.QrCodeQueryPort
import finda.findavolunteer.domain.qrcode.model.QrCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class QrCodePersistenceAdapter(
    private val qrCodeRepository: QrCodeRepository,
    private val qrCodeMapper: QrCodeMapper
) : QrCodeCommandPort, QrCodeQueryPort {
    override fun save(qrCode: QrCode): QrCode {
        val entity = qrCodeRepository.save(qrCodeMapper.toEntity(qrCode))
        return qrCodeMapper.toDomain(entity)
    }

    override fun findById(id: UUID): QrCode? {
        val entity = qrCodeRepository.findByIdOrNull(id)
        return entity?.let(qrCodeMapper::toDomain)
    }
}
