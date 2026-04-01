package finda.findavolunteer.infrastructure.pdf.drawer

import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import finda.findavolunteer.infrastructure.pdf.model.TableRow
import finda.findavolunteer.infrastructure.pdf.row.DescriptionRow
import finda.findavolunteer.infrastructure.pdf.row.GroupTypeRow
import finda.findavolunteer.infrastructure.pdf.row.OrgRow
import finda.findavolunteer.infrastructure.pdf.row.SimpleRow
import finda.findavolunteer.infrastructure.pdf.row.VolunteerTypeRow
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.time.LocalDate

object InfoTableDrawer {

    fun draw(
        cs: PDPageContentStream,
        font: PDType0Font,
        semiBoldFont: PDType0Font,
        x: Float,
        contentW: Float,
        topY: Float,
        data: VolunteerDocumentData
    ): Float {

        val rows: List<TableRow> = listOf(
            GroupTypeRow(data.groupVolunteerType),
            SimpleRow(listOf("봉사활동", "인원"), buildPersonnelText(data)),
            SimpleRow(
                listOf("봉사활동", "기간"),
                "${formatDate(data.startDate)} ~ ${formatDate(data.endDate)}"
            ),
            OrgRow,
            VolunteerTypeRow(data.volunteerType),
            SimpleRow(listOf("활동", "장소"), "", centerAlign = false),
            DescriptionRow(data.description)
        )

        var y = topY
        rows.forEach {
            y = it.render(cs, font, semiBoldFont, x, y, contentW)
        }
        return y
    }

    private fun buildPersonnelText(data: VolunteerDocumentData): String {
        val first = data.participants.firstOrNull()
        val total = data.participants.size

        return when {
            first != null && total > 1 ->
                "${first.grade}학년 ${first.classNum}반  성명: ${first.name} 외 ${total - 1}명 (총 ${total}명)"

            first != null ->
                "${first.grade}학년 ${first.classNum}반  성명: ${first.name} (총 1명)"

            else -> "-"
        }
    }

    private fun formatDate(date: LocalDate) =
        "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"
}
