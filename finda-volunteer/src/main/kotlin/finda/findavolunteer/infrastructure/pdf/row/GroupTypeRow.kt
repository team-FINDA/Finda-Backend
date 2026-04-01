package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.domain.volunteer.enum.GroupVolunteerType
import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.model.TableRow.Companion.drawLabelCell
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data class GroupTypeRow(val type: GroupVolunteerType) : TableRow {
    override fun render(cs: PDPageContentStream, font: PDType0Font, semiBoldFont: PDType0Font, x: Float, y: Float, contentW: Float): Float {
        drawLabelCell(cs, semiBoldFont, x, y, contentW, listOf("단체봉사", "구분"))

        val size   = DocumentLayout.fontSizeSection
        val markW  = font.getStringWidth("○") / 1000f * size
        val closeW = font.getStringWidth(")") / 1000f * size
        val textY  = y - DocumentLayout.stdRowH + (DocumentLayout.stdRowH - size) / 2f

        val part1  = "정규교육과정 내 봉사활동("
        val part1W = font.getStringWidth(part1) / 1000f * size
        val part2  = ") / 정규교육과정 외의 봉사활동("
        val part2W = font.getStringWidth(part2) / 1000f * size

        val totalW = part1W + markW + part2W + markW + closeW
        val valueW = contentW - DocumentLayout.labelW
        val startX = x + DocumentLayout.labelW + (valueW - totalW) / 2f

        PdfDrawingUtils.drawText(cs, font, size, startX, textY, part1)
        if (type == GroupVolunteerType.CURRICULAR) {
            PdfDrawingUtils.drawText(cs, font, size, startX + part1W, textY, "○")
        }
        PdfDrawingUtils.drawText(cs, font, size, startX + part1W + markW, textY, part2)
        if (type == GroupVolunteerType.EXTRACURRICULAR) {
            PdfDrawingUtils.drawText(cs, font, size, startX + part1W + markW + part2W, textY, "○")
        }
        PdfDrawingUtils.drawText(cs, font, size, startX + part1W + markW + part2W + markW, textY, ")")

        return y - DocumentLayout.stdRowH
    }
}
