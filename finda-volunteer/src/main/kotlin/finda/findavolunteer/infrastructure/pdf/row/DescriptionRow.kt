package finda.findavolunteer.infrastructure.pdf.row

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.model.TableRow.Companion.drawLabelCell
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font

data class DescriptionRow(val description: String) : TableRow {
    override fun render(cs: PDPageContentStream, font: PDType0Font, semiBoldFont: PDType0Font, x: Float, y: Float, contentW: Float): Float {
        drawLabelCell(cs, semiBoldFont, x, y, contentW, listOf("활동", "내용"))
        PdfDrawingUtils.drawText(
            cs, font, DocumentLayout.fontSizeSection,
            x + DocumentLayout.labelW + 4f,
            y - DocumentLayout.stdRowH + (DocumentLayout.stdRowH - DocumentLayout.fontSizeSection) / 2f,
            description
        )
        return y - DocumentLayout.stdRowH
    }
}
