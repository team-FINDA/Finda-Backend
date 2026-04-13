package finda.findavolunteer.infrastructure.pdf.model

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

interface TableRow {

    fun render(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        y: Float,
        contentW: Float
    ): Float

    companion object {
        fun drawLabelCell(
            cs: PDPageContentStream,
            semiBoldFont: PDType0Font,
            x: Float,
            y: Float,
            contentW: Float,
            labelLines: List<String>,
            rowH: Float = DocumentLayout.stdRowH
        ) {
            PdfDrawingUtils.fillRect(cs, x, y - rowH, DocumentLayout.labelW, rowH, DocumentLayout.labelBgColor)
            PdfDrawingUtils.drawRect(cs, x, y - rowH, DocumentLayout.labelW, rowH)
            PdfDrawingUtils.drawRect(cs, x + DocumentLayout.labelW, y - rowH, contentW - DocumentLayout.labelW, rowH)
            PdfDrawingUtils.drawCellLabel(cs, semiBoldFont, x, y, rowH, labelLines)
        }
    }
}
