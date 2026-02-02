package finda.findanotification.adapter.out.persistence.notice.repository

import finda.findanotification.adapter.out.persistence.notice.entity.NoticeJpaEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface NoticeRepository : CrudRepository<NoticeJpaEntity, UUID>
