package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data object OrgRow : TableRow {
    override fun render(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        y: Float,
        contentW: Float
    ): Float {

        val labelW = DocumentLayout.labelW
        val valueW = (contentW - labelW * 2) / 2f

        PdfDrawingUtils.fillRect(cs, x, y - DocumentLayout.stdRowH, labelW, DocumentLayout.stdRowH, DocumentLayout.labelBgColor)
        PdfDrawingUtils.fillRect(cs, x + labelW + valueW, y - DocumentLayout.stdRowH, labelW, DocumentLayout.stdRowH, DocumentLayout.labelBgColor)
        PdfDrawingUtils.drawRect(cs, x, y - DocumentLayout.stdRowH, labelW, DocumentLayout.stdRowH)
        PdfDrawingUtils.drawRect(cs, x + labelW, y - DocumentLayout.stdRowH, valueW, DocumentLayout.stdRowH)
        PdfDrawingUtils.drawRect(cs, x + labelW + valueW, y - DocumentLayout.stdRowH, labelW, DocumentLayout.stdRowH)
        PdfDrawingUtils.drawRect(cs, x + labelW + valueW + labelW, y - DocumentLayout.stdRowH, valueW, DocumentLayout.stdRowH)

        PdfDrawingUtils.drawCellLabel(cs, semiBoldFont, x, y, DocumentLayout.stdRowH, listOf("봉사활동", "기관"))
        PdfDrawingUtils.drawCellLabel(cs, semiBoldFont, x + labelW + valueW, y, DocumentLayout.stdRowH, listOf("기관", "연락처"))

        return y - DocumentLayout.stdRowH
    }
}
