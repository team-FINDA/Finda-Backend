package finda.findavolunteer.infrastructure.pdf.util

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.awt.Color

object PdfDrawingUtils {

    fun drawCellLabel(
        cs: PDPageContentStream,
        font: PDType0Font,
        cellX: Float, cellTopY: Float, cellH: Float,
        labelLines: List<String>,
        size: Float = DocumentLayout.fontSizeSection
    ) {
        val lineH      = size + 3f
        val totalTextH = labelLines.size * lineH
        var ty         = cellTopY - (cellH - totalTextH) / 2f - size + 2f
        labelLines.forEach { line ->
            val tw = font.getStringWidth(line) / 1000f * size
            drawText(cs, font, size, cellX + (DocumentLayout.labelW - tw) / 2f, ty, line)
            ty -= lineH
        }
    }

    fun fillRect(cs: PDPageContentStream, x: Float, y: Float, w: Float, h: Float, color: Color) {
        cs.setNonStrokingColor(color)
        cs.addRect(x, y, w, h)
        cs.fill()
        cs.setNonStrokingColor(DocumentLayout.textColor)
    }

    fun drawRect(cs: PDPageContentStream, x: Float, y: Float, w: Float, h: Float) {
        cs.setLineWidth(DocumentLayout.strokeWidth)
        cs.addRect(x, y, w, h)
        cs.stroke()
    }

    fun drawText(cs: PDPageContentStream, font: PDType0Font, size: Float, x: Float, y: Float, text: String) {
        cs.beginText()
        cs.setFont(font, size)
        cs.newLineAtOffset(x, y)
        cs.showText(text)
        cs.endText()
    }

    fun drawTextCentered(cs: PDPageContentStream, font: PDType0Font, size: Float, containerW: Float, y: Float, text: String) {
        val tw = font.getStringWidth(text) / 1000f * size
        drawText(cs, font, size, (containerW - tw) / 2f, y, text)
    }
}
