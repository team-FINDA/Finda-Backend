package finda.findanotification.adapter.out.persistence.notice

import finda.findanotification.adapter.out.persistence.notice.mapper.NoticeMapper
import finda.findanotification.adapter.out.persistence.notice.repository.NoticeRepository
import finda.findanotification.application.port.out.notice.DeleteNoticePort
import finda.findanotification.application.port.out.notice.GetNoticePort
import finda.findanotification.application.port.out.notice.SaveNoticePort
import finda.findanotification.application.port.out.notice.UpdateNoticePort
import finda.findanotification.domain.notice.model.Notice
import finda.findanotification.domain.notice.type.Status
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NoticePersistenceAdapter(
    private val noticeRepository: NoticeRepository,
    private val noticeMapper: NoticeMapper
) : SaveNoticePort, DeleteNoticePort, GetNoticePort, UpdateNoticePort {

    override fun save(notice: Notice): Notice {
        val entity = noticeMapper.toEntity(notice)
        val saved = noticeRepository.save(entity)
        return noticeMapper.toDomain(saved)!!
    }

    override fun delete(notice: Notice) {
        noticeRepository.deleteById(notice.id)
    }

    override fun findAll(): List<Notice> {
        return noticeRepository.findAll()
            .map { noticeMapper.toDomain(it)!! }
    }

    override fun findById(id: UUID): Notice? {
        return noticeRepository.findByIdOrNull(id)
            ?.let { noticeMapper.toDomain(it) }
    }

    override fun update(notice: Notice): Notice {
        val entity = noticeMapper.toEntity(notice)
        val saved = noticeRepository.save(entity)
        return noticeMapper.toDomain(saved)!!
    }

    override fun updateStatus(noticeId: UUID, status: Status) {
        val notice = findById(noticeId) ?: return
        val entity = noticeMapper.toEntity(
            notice.copy(status = status)
        )
        noticeRepository.save(entity)
    }
}
