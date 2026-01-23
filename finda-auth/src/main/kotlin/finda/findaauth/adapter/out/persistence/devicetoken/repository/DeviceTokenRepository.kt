package finda.findaauth.adapter.out.persistence.devicetoken.repository

import finda.findaauth.adapter.out.persistence.devicetoken.entity.DeviceTokenJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DeviceTokenRepository : CrudRepository<DeviceTokenJpaEntity, UUID>
