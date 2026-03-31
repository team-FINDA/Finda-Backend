package finda.findavolunteer.adapter.out.document

import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.ParticipantInfo
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.time.LocalDate

@Component
class VolunteerDocumentGeneratorAdapter : GenerateVolunteerDocumentPort {

    private val fontBytes = ClassPathResource("fonts/NanumGothic.ttf").inputStream.readBytes()

    override fun generate(data: VolunteerDocumentData): ByteArray {
        PDDocument().use { doc ->
            val font = PDType0Font.load(doc, fontBytes.inputStream(), true)
            val boldFont = PDType0Font.load(doc, fontBytes.inputStream(), true)
            val page = PDPage(PDRectangle.A4)
            doc.addPage(page)

            val pageWidth = page.mediaBox.width
            val margin = 50f
            val contentWidth = pageWidth - margin * 2
            var y = page.mediaBox.height - margin

            PDPageContentStream(doc, page).use { cs ->
                y = drawTitle(cs, boldFont, pageWidth, y)
                y = drawInfoTable(cs, font, boldFont, margin, contentWidth, y, data)
                y = drawParticipantsTable(cs, font, boldFont, margin, contentWidth, y, data.participants)
                drawFooter(cs, font, pageWidth, margin, y, data.teacherName)
            }

            ByteArrayOutputStream().use { out ->
                doc.save(out)
                return out.toByteArray()
            }
        }
    }

    private fun drawTitle(cs: PDPageContentStream, font: PDType0Font, pageWidth: Float, y: Float): Float {
        val text = "봉사활동 확인서"
        val size = 18f
        cs.beginText()
        cs.setFont(font, size)
        cs.newLineAtOffset((pageWidth - font.getStringWidth(text) / 1000 * size) / 2, y)
        cs.showText(text)
        cs.endText()
        return y - 40f
    }

    private fun drawInfoTable(
        cs: PDPageContentStream,
        font: PDType0Font,
        boldFont: PDType0Font,
        margin: Float,
        contentWidth: Float,
        y: Float,
        data: VolunteerDocumentData
    ): Float {
        val first = data.participants.firstOrNull()
        val total = data.participants.size
        val participantSummary = when {
            first != null && total > 1 -> "${first.grade}학년 ${first.classNum}반  성명: ${first.name} 외 ${total - 1}명 (총 ${total}명)"
            first != null -> "${first.grade}학년 ${first.classNum}반  성명: ${first.name} (총 1명)"
            else -> "-"
        }

        val rows = listOf(
            "봉사 활동 인원" to participantSummary,
            "봉사 활동 기간" to "${formatDate(data.startDate)} ~ ${formatDate(data.endDate)}",
            "활동 내용" to data.description
        )

        val labelWidth = 120f
        val valueWidth = contentWidth - labelWidth
        val rowHeight = 25f
        val fontSize = 10f
        var currentY = y

        rows.forEach { (label, value) ->
            cs.addRect(margin, currentY - rowHeight, labelWidth, rowHeight)
            cs.addRect(margin + labelWidth, currentY - rowHeight, valueWidth, rowHeight)
            cs.stroke()
            drawText(cs, boldFont, fontSize, margin + 4f, currentY - rowHeight + 8f, label)
            drawText(cs, font, fontSize, margin + labelWidth + 4f, currentY - rowHeight + 8f, value)
            currentY -= rowHeight
        }

        return currentY - 20f
    }

    private fun drawParticipantsTable(
        cs: PDPageContentStream,
        font: PDType0Font,
        boldFont: PDType0Font,
        margin: Float,
        contentWidth: Float,
        y: Float,
        participants: List<ParticipantInfo>
    ): Float {
        drawText(cs, boldFont, 12f, margin, y, "봉사활동 참여자 명단")
        var currentY = y - 25f

        val headers = listOf("학년", "반", "번호", "이름", "인정시간")
        val colWidths = listOf(50f, 50f, 50f, contentWidth - 250f, 80f)
        val rowHeight = 25f
        val fontSize = 10f

        drawTableRow(cs, boldFont, fontSize, margin, currentY, rowHeight, headers, colWidths, center = true)
        currentY -= rowHeight

        participants.forEach { p ->
            val values = listOf(p.grade.toString(), p.classNum.toString(), p.num.toString(), p.name, "${p.recognizedHours}시간")
            drawTableRow(cs, font, fontSize, margin, currentY, rowHeight, values, colWidths, center = true)
            currentY -= rowHeight
        }

        return currentY - 20f
    }

    private fun drawTableRow(
        cs: PDPageContentStream,
        font: PDType0Font,
        fontSize: Float,
        startX: Float,
        y: Float,
        rowHeight: Float,
        values: List<String>,
        colWidths: List<Float>,
        center: Boolean = false
    ) {
        var x = startX
        values.forEachIndexed { i, v ->
            cs.addRect(x, y - rowHeight, colWidths[i], rowHeight)
            cs.stroke()
            val textX = if (center) x + (colWidths[i] - font.getStringWidth(v) / 1000 * fontSize) / 2 else x + 4f
            drawText(cs, font, fontSize, textX, y - rowHeight + 8f, v)
            x += colWidths[i]
        }
    }

    private fun drawFooter(cs: PDPageContentStream, font: PDType0Font, pageWidth: Float, margin: Float, y: Float, teacherName: String) {
        val today = LocalDate.now()
        val dateText = "작성일: ${today.year}년 ${today.monthValue}월 ${today.dayOfMonth}일"
        val teacherText = "확인자  교사: $teacherName"

        drawText(cs, font, 10f, pageWidth - margin - font.getStringWidth(dateText) / 1000 * 10f, y, dateText)
        drawText(cs, font, 10f, pageWidth - margin - font.getStringWidth(teacherText) / 1000 * 10f, y - 20f, teacherText)
    }

    private fun drawText(cs: PDPageContentStream, font: PDType0Font, size: Float, x: Float, y: Float, text: String) {
        cs.beginText()
        cs.setFont(font, size)
        cs.newLineAtOffset(x, y)
        cs.showText(text)
        cs.endText()
    }

    private fun formatDate(date: LocalDate) =
        "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"
}
