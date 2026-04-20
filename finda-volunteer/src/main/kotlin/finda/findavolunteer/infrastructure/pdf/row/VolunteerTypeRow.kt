package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.domain.volunteer.enum.VolunteerType
import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.model.TableRow.Companion.drawLabelCell
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data class VolunteerTypeRow(val type: VolunteerType) : TableRow {

    private data class Item(val label: String, val volunteerType: VolunteerType)

    override fun render(cs: PDPageContentStream, font: PDType0Font, semiBoldFont: PDType0Font, x: Float, y: Float, contentW: Float): Float {
        drawLabelCell(cs, semiBoldFont, x, y, contentW, listOf("봉사", "영역"))

        val size = DocumentLayout.fontSizeSection
        val markW = font.getStringWidth("○") / 1000f * size
        val closeW = font.getStringWidth(")") / 1000f * size
        val gapW = font.getStringWidth("   ") / 1000f * size
        val textY = y - DocumentLayout.stdRowH + (DocumentLayout.stdRowH - size) / 2f

        val items = listOf(
            Item("이웃돕기활동", VolunteerType.NEIGHBOR_SUPPORT),
            Item("환경보호활동", VolunteerType.ENVIRONMENT),
            Item("캠페인활동", VolunteerType.CAMPAIGN),
            Item("기타", VolunteerType.OTHER)
        )

        val totalW = items.mapIndexed { index, item ->
            val labelW = font.getStringWidth("${item.label}(") / 1000f * size
            labelW + markW + closeW + if (index < items.lastIndex) gapW else 0f
        }.sum()

        val valueW = contentW - DocumentLayout.labelW
        val leftPadding = 4f
        var curX = (x + DocumentLayout.labelW + (valueW - totalW) / 2f)
            .coerceAtLeast(x + DocumentLayout.labelW + leftPadding)

        items.forEach { item ->
            val labelText = "${item.label}("
            val labelW = font.getStringWidth(labelText) / 1000f * size
            PdfDrawingUtils.drawText(cs, font, size, curX, textY, labelText)
            if (type == item.volunteerType) {
                PdfDrawingUtils.drawText(cs, font, size, curX + labelW, textY, "○")
            }
            PdfDrawingUtils.drawText(cs, font, size, curX + labelW + markW, textY, ")")
            curX += labelW + markW + closeW + gapW
        }

        return y - DocumentLayout.stdRowH
    }
}
