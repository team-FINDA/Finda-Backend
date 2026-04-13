package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.model.TableRow.Companion.drawLabelCell
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data class DescriptionRow(val description: String) : TableRow {
    override fun render(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        y: Float,
        contentW: Float
    ): Float {
        val textX = x + DocumentLayout.labelW + 4f
        val textW = contentW - DocumentLayout.labelW - 8f
        val lines = wrapText(font, description, textW)
        val lineH = DocumentLayout.fontSizeSection * 1.5f
        val rowH = (lines.size * lineH + 8f).coerceAtLeast(DocumentLayout.stdRowH)

        drawLabelCell(cs, semiBoldFont, x, y, contentW, listOf("활동", "내용"), rowH)

        var ty = y - (rowH - lines.size * lineH) / 2f - DocumentLayout.fontSizeSection
        lines.forEach { line ->
            PdfDrawingUtils.drawText(cs, font, DocumentLayout.fontSizeSection, textX, ty, line)
            ty -= lineH
        }

        return y - rowH
    }

    private fun wrapText(font: PDType0Font, text: String, maxWidth: Float): List<String> {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var current = ""

        words.forEach { word ->
            val candidate = if (current.isEmpty()) word else "$current $word"
            val width = font.getStringWidth(candidate) / 1000f * DocumentLayout.fontSizeSection
            if (width > maxWidth && current.isNotEmpty()) {
                lines.add(current)
                current = word
            } else {
                current = candidate
            }
        }
        if (current.isNotEmpty()) lines.add(current)
        return lines
    }
}
