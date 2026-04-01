package finda.findavolunteer.infrastructure.pdf.drawer

import finda.findavolunteer.infrastructure.pdf.layout.DocumentLayout
import finda.findavolunteer.infrastructure.pdf.util.PdfDrawingUtils
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.time.LocalDate

object FooterDrawer {

    fun draw(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        contentW: Float,
        topY: Float,
        teacherName: String
    ) {
        val cellH = 116f
        val today = LocalDate.now()
        val confirmText = "위 학생들은 위와 같이 단체봉사활동에 참여하였으며 이 기재내용은 사실과 틀림없음을 확인합니다."
        val dateText = "${today.year}년  ${today.monthValue}월  ${today.dayOfMonth}일"
        val sigText = "확 인 자 : 직위  교사    $teacherName  (인)"
        val pageW = x * 2 + contentW

        PdfDrawingUtils.drawRect(cs, x, topY - cellH, contentW, cellH)
        PdfDrawingUtils.drawTextCentered(
            cs,
            font,
            10f,
            pageW,
            topY - cellH * DocumentLayout.footerLine1Ratio,
            confirmText
        )
        PdfDrawingUtils.drawTextCentered(
            cs,
            semiBoldFont,
            DocumentLayout.fontSizeFooter,
            pageW,
            topY - cellH * DocumentLayout.footerLine2Ratio,
            dateText
        )
        PdfDrawingUtils.drawTextCentered(
            cs,
            semiBoldFont,
            DocumentLayout.fontSizeFooter,
            pageW,
            topY - cellH * DocumentLayout.footerLine3Ratio,
            sigText
        )
    }
}
