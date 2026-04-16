package finda.findavolunteer.adapter.out.persistence.qrcode.repository

import finda.findavolunteer.adapter.out.persistence.qrcode.entity.QrCodeJpaEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface QrCodeRepository : CrudRepository<QrCodeJpaEntity, UUID> {
    fun findByCode(qrCode: String): QrCodeJpaEntity?
    fun existsByCode(qrCode: String): Boolean

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select q from QrCodeJpaEntity q where q.code = :qrCode")
    fun findByQrCodeForUpdate(qrCode: String): QrCodeJpaEntity?
}
