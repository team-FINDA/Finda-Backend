package finda.findavolunteer.adapter.out.persistence.qrcode.repository

import finda.findavolunteer.adapter.out.persistence.qrcode.entity.QrCodeJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface QrCodeRepository : CrudRepository<QrCodeJpaEntity, UUID>
