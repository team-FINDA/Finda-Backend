package finda.findanotification.adapter.out.persistence.notice

import finda.findanotification.adapter.out.persistence.notice.mapper.NoticeMapper
import finda.findanotification.adapter.out.persistence.notice.repository.NoticeRepository
import finda.findanotification.application.port.out.notice.DeleteNoticePort
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.domain.notice.model.Notice
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NoticePersistenceAdapter(
    private val noticeRepository: NoticeRepository,
    private val noticeMapper: NoticeMapper
) : SaveNoticePort, DeleteNoticePort {

    override fun save(notice: Notice): Notice {
        val entity = noticeMapper.toEntity(notice)
        val saved = noticeRepository.save(entity)
        return noticeMapper.toDomain(saved)!!
    }

    override fun delete(id: UUID) {
        noticeRepository.deleteById(id)
    }
}
