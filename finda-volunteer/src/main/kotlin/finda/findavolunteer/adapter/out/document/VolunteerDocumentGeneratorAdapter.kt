package finda.findavolunteer.adapter.out.document

import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.drawer.FooterDrawer
import finda.findavolunteer.infrastructure.pdf.drawer.InfoTableDrawer
import finda.findavolunteer.infrastructure.pdf.drawer.ParticipantTableDrawer
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils.drawTextCentered
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream

@Component
class VolunteerDocumentGeneratorAdapter : GenerateVolunteerDocumentPort {

    private val fontBytes by lazy {
        ClassPathResource("fonts/Pretendard-Regular.ttf").inputStream.use { it.readBytes() }
    }
    private val semiBoldFontBytes by lazy {
        ClassPathResource("fonts/Pretendard-SemiBold.ttf").inputStream.use { it.readBytes() }
    }

    override fun generate(data: VolunteerDocumentData): ByteArray {
        PDDocument().use { doc ->
            val font         = PDType0Font.load(doc, fontBytes.inputStream(), true)
            val semiBoldFont = PDType0Font.load(doc, semiBoldFontBytes.inputStream(), true)

            drawFirstPage(doc, font, semiBoldFont, data)

            val chunks = data.participants.drop(ParticipantTableDrawer.maxPerPage())
                .chunked(ParticipantTableDrawer.maxPerPage())

            chunks.forEachIndexed { index, chunk ->
                val startSeq = (index + 1) * ParticipantTableDrawer.maxPerPage() + 1
                drawParticipantOnlyPage(doc, font, semiBoldFont, chunk, startSeq, data.teacherName)
            }

            ByteArrayOutputStream().use { out ->
                doc.save(out)
                return out.toByteArray()
            }
        }
    }

    private fun drawFirstPage(
        doc: PDDocument,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        data: VolunteerDocumentData
    ) {
        val page     = PDPage(PDRectangle.A4)
        doc.addPage(page)
        val pageW    = page.mediaBox.width
        val pageH    = page.mediaBox.height
        val contentW = pageW - DocumentLayout.margin * 2

        PDPageContentStream(doc, page).use { cs ->
            var y = pageH - DocumentLayout.margin - DocumentLayout.titleBottomPadding
            y = drawTitle(cs, semiBoldFont, pageW, y)
            y = InfoTableDrawer.draw(cs, font, semiBoldFont, DocumentLayout.margin, contentW, y - 8f, data)
            y = ParticipantTableDrawer.draw(
                cs, font, semiBoldFont, DocumentLayout.margin, contentW, y,
                participants = data.participants.take(ParticipantTableDrawer.maxPerPage()),
                startSeq = 1
            )
            FooterDrawer.draw(cs, font, semiBoldFont, DocumentLayout.margin, contentW, y, data.teacherName)
        }
    }

    private fun drawParticipantOnlyPage(
        doc: PDDocument,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        participants: List<finda.findavolunteer.domain.volunteer.data.ParticipantInfo>,
        startSeq: Int,
        teacherName: String
    ) {
        val page     = PDPage(PDRectangle.A4)
        doc.addPage(page)
        val pageW    = page.mediaBox.width
        val pageH    = page.mediaBox.height
        val contentW = pageW - DocumentLayout.margin * 2

        PDPageContentStream(doc, page).use { cs ->
            var y = pageH - DocumentLayout.margin
            y = ParticipantTableDrawer.draw(
                cs, font, semiBoldFont, DocumentLayout.margin, contentW, y,
                participants = participants,
                startSeq = startSeq
            )
            FooterDrawer.draw(cs, font, semiBoldFont, DocumentLayout.margin, contentW, y, teacherName)
        }
    }

    private fun drawTitle(cs: PDPageContentStream, font: PDType0Font, pageW: Float, y: Float): Float {
        drawTextCentered(cs, font, DocumentLayout.fontSizeTitle, pageW, y, "학교교육계획에 의한 단체봉사활동 실시 확인서")
        return y - DocumentLayout.fontSizeTitle * DocumentLayout.titleLineHeightRatio
    }
}
