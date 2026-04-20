package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.model.TableRow.Companion.drawLabelCell
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data class SimpleRow(
    val labelLines: List<String>,
    val valueText: String,
    val centerAlign: Boolean = true
) : TableRow {
    override fun render(cs: PDPageContentStream, font: PDType0Font, semiBoldFont: PDType0Font, x: Float, y: Float, contentW: Float): Float {
        drawLabelCell(cs, semiBoldFont, x, y, contentW, labelLines)

        val size = DocumentLayout.fontSizeSection
        val textY = y - DocumentLayout.stdRowH + (DocumentLayout.stdRowH - size) / 2f
        val textX = if (centerAlign) {
            val valueW = contentW - DocumentLayout.labelW
            val textW = font.getStringWidth(valueText) / 1000f * size
            x + DocumentLayout.labelW + (valueW - textW) / 2f
        } else {
            x + DocumentLayout.labelW + 4f
        }

        PdfDrawingUtils.drawText(cs, font, size, textX, textY, valueText)
        return y - DocumentLayout.stdRowH
    }
}
