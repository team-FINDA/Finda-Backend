package finda.findanotification.adapter.out.persistence.notice.mapper

import finda.findanotification.adapter.out.persistence.GenericMapper
import finda.findanotification.adapter.out.persistence.notice.entity.NoticeJpaEntity
import finda.findanotification.domain.notice.model.Notice
import org.springframework.stereotype.Component

@Component
class NoticeMapper : GenericMapper<Notice, NoticeJpaEntity> {

    override fun toDomain(entity: NoticeJpaEntity?): Notice? {
        return entity?.let {
            Notice(
                id = it.id!!,
                title = it.title,
                body = it.body,
                userId = it.userId,
                status = it.status,
                noticeDate = it.noticeDate,
                noticeTime = it.noticeTime
            )
        }
    }

    override fun toEntity(domain: Notice): NoticeJpaEntity {
        return NoticeJpaEntity(
            id = domain.id,
            title = domain.title,
            body = domain.body,
            userId = domain.userId,
            status = domain.status,
            noticeDate = domain.noticeDate,
            noticeTime = domain.noticeTime
        )
    }
}
