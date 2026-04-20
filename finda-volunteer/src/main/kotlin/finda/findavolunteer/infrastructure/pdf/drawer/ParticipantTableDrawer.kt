package finda.findavolunteer.infrastructure.pdf.drawer

import finda.findavolunteer.domain.volunteer.data.ParticipantInfo
import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import kotlin.math.ceil

object ParticipantTableDrawer {

    private const val MAX_PER_PAGE = 30

    fun draw(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        contentW: Float,
        topY: Float,
        participants: List<ParticipantInfo>,
        startSeq: Int = 1
    ): Float {
        val sectionH = 25f
        val halfW = DocumentLayout.pColWidths.sum()
        var y = topY

        PdfDrawingUtils.fillRect(cs, x, y - sectionH, contentW, sectionH, DocumentLayout.labelBgColor)
        PdfDrawingUtils.drawRect(cs, x, y - sectionH, contentW, sectionH)
        PdfDrawingUtils.drawTextCentered(
            cs,
            semiBoldFont,
            DocumentLayout.fontSizeSubTitle,
            contentW + x * 2,
            y - sectionH + (sectionH - DocumentLayout.fontSizeSubTitle) / 2f,
            "봉사활동 참여자 명단"
        )
        y -= sectionH

        val headers = listOf("순", "학년", "반", "번호", "성명", "인정\n시간")
        drawCells(cs, semiBoldFont, x, y, headers, isHeader = true)
        drawCells(cs, semiBoldFont, x + halfW, y, headers, isHeader = true)
        y -= DocumentLayout.participantRowH

        val rows = minOf(15, ceil(participants.size / 2.0).toInt())
        for (i in 0 until rows) {
            drawCells(cs, font, x, y, rowValues(startSeq + i, participants.getOrNull(i)))
            drawCells(cs, font, x + halfW, y, rowValues(startSeq + i + rows, participants.getOrNull(i + rows)))
            y -= DocumentLayout.participantRowH
        }

        return y
    }

    fun maxPerPage() = MAX_PER_PAGE

    private fun rowValues(seq: Int, p: ParticipantInfo?): List<String> =
        if (p != null) {
            listOf(
                seq.toString(),
                p.grade.toString(),
                p.classNum.toString(),
                p.num.toString(),
                p.name,
                p.recognizedHours.toString()
            )
        } else {
            listOf(seq.toString(), "", "", "", "", "")
        }

    private fun drawCells(
        cs: PDPageContentStream,
        font: PDType0Font,
        startX: Float,
        topY: Float,
        values: List<String>,
        isHeader: Boolean = false
    ) {
        var cx = startX
        values.forEachIndexed { i, v ->
            val cw = DocumentLayout.pColWidths[i]
            val lines = v.split("\n")
            val lineH = DocumentLayout.fontSizeSection

            if (isHeader || i == 0) {
                PdfDrawingUtils.fillRect(
                    cs,
                    cx,
                    topY - DocumentLayout.participantRowH,
                    cw,
                    DocumentLayout.participantRowH,
                    DocumentLayout.labelBgColor
                )
            }
            PdfDrawingUtils.drawRect(cs, cx, topY - DocumentLayout.participantRowH, cw, DocumentLayout.participantRowH)

            val totalH = lines.size * lineH
            var ty = topY - (DocumentLayout.participantRowH - totalH) / 2f - lineH + 2f
            lines.forEach { line ->
                val tw = font.getStringWidth(line) / 1000f * DocumentLayout.fontSizeSection
                PdfDrawingUtils.drawText(cs, font, DocumentLayout.fontSizeSection, cx + (cw - tw) / 2f, ty, line)
                ty -= lineH
            }
            cx += cw
        }
    }
}
