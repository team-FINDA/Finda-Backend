package finda.findavolunteer.adapter.out.document

import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import finda.findavolunteer.infrastructure.pdf.drawer.FooterDrawer
import finda.findavolunteer.infrastructure.pdf.drawer.InfoTableDrawer
import finda.findavolunteer.infrastructure.pdf.drawer.ParticipantTableDrawer
import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
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
            val font = PDType0Font.load(doc, fontBytes.inputStream(), true)
            val semiBoldFont = PDType0Font.load(doc, semiBoldFontBytes.inputStream(), true)

            val drawnOnFirstPage = drawFirstPage(doc, font, semiBoldFont, data)

            val chunks = data.participants
                .drop(drawnOnFirstPage)
                .chunked(ParticipantTableDrawer.maxPerPage())

            chunks.forEachIndexed { index, chunk ->
                val startSeq = drawnOnFirstPage + index * ParticipantTableDrawer.maxPerPage() + 1
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
    ): Int {
        val page = PDPage(PDRectangle.A4)
        doc.addPage(page)
        val pageW = page.mediaBox.width
        val pageH = page.mediaBox.height
        val contentW = pageW - DocumentLayout.margin * 2
        val bottomLimit = DocumentLayout.margin

        PDPageContentStream(doc, page).use { cs ->
            var y = pageH - DocumentLayout.margin - DocumentLayout.titleBottomPadding
            y = drawTitle(cs, semiBoldFont, pageW, y)
            y = InfoTableDrawer.draw(cs, font, semiBoldFont, DocumentLayout.margin, contentW, y - 8f, data)

            val footerH = 116f
            val participantHeaderH = 25f
            val rowH = DocumentLayout.participantRowH
            val remaining = y - bottomLimit

            if (remaining < participantHeaderH + rowH + footerH) {
                return 0
            }

            val availableForRows = remaining - participantHeaderH - footerH
            val fittableRows = (availableForRows / rowH).toInt().coerceAtMost(15)
            val fittableParticipants = (fittableRows * 2)
                .coerceAtMost(data.participants.size)
                .coerceAtMost(ParticipantTableDrawer.maxPerPage())

            y = ParticipantTableDrawer.draw(
                cs,
                font,
                semiBoldFont,
                DocumentLayout.margin,
                contentW,
                y,
                participants = data.participants.take(fittableParticipants),
                startSeq = 1
            )
            FooterDrawer.draw(cs, font, semiBoldFont, DocumentLayout.margin, contentW, y, data.teacherName)

            return fittableParticipants
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
        val page = PDPage(PDRectangle.A4)
        doc.addPage(page)
        val pageW = page.mediaBox.width
        val pageH = page.mediaBox.height
        val contentW = pageW - DocumentLayout.margin * 2

        PDPageContentStream(doc, page).use { cs ->
            var y = pageH - DocumentLayout.margin
            y = ParticipantTableDrawer.draw(
                cs,
                font,
                semiBoldFont,
                DocumentLayout.margin,
                contentW,
                y,
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
