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
            labelLines: List<String>
        ) {
            PdfDrawingUtils.fillRect(cs, x, y - DocumentLayout.stdRowH, DocumentLayout.labelW, DocumentLayout.stdRowH, DocumentLayout.labelBgColor)
            PdfDrawingUtils.drawRect(cs, x, y - DocumentLayout.stdRowH, DocumentLayout.labelW, DocumentLayout.stdRowH)
            PdfDrawingUtils.drawRect(cs, x + DocumentLayout.labelW, y - DocumentLayout.stdRowH, contentW - DocumentLayout.labelW, DocumentLayout.stdRowH)
            PdfDrawingUtils.drawCellLabel(cs, semiBoldFont, x, y, DocumentLayout.stdRowH, labelLines)
        }
    }
}
