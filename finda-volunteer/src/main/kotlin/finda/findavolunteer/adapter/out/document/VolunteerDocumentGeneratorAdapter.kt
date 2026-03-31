package finda.findavolunteer.adapter.out.document

import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.ParticipantInfo
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.time.LocalDate

@Component
class VolunteerDocumentGeneratorAdapter : GenerateVolunteerDocumentPort {

    override fun generate(data: VolunteerDocumentData): ByteArray {
        XWPFDocument().use { doc ->
            setupPageLayout(doc)

            addTitle(doc, "봉사활동 확인서")
            addInfoTable(doc, data)
            addParticipantsTable(doc, data.participants)
            addFooter(doc, data.teacherName)

            ByteArrayOutputStream().use { out ->
                doc.write(out)
                return out.toByteArray()
            }
        }
    }

    private fun setupPageLayout(doc: XWPFDocument) {
        val sectPr = doc.document.body.addNewSectPr()

        val pgSz: CTPageSz = sectPr.addNewPgSz()
        pgSz.w = A4_WIDTH
        pgSz.h = A4_HEIGHT

        val pgMar: CTPageMar = sectPr.addNewPgMar()
        pgMar.top = MARGIN
        pgMar.bottom = MARGIN
        pgMar.left = MARGIN
        pgMar.right = MARGIN
    }

    private fun addTitle(doc: XWPFDocument, text: String) {
        doc.createParagraph().apply {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 400
        }.createRun().apply {
            setText(text)
            isBold = true
            fontSize = 18
            fontFamily = FONT
        }
    }

    private fun addInfoTable(doc: XWPFDocument, data: VolunteerDocumentData) {
        val first = data.participants.firstOrNull()
        val total = data.participants.size

        val participantSummary = if (first != null && total > 1) {
            "${first.grade}학년 ${first.classNum}반  성명: ${first.name} 외 ${total - 1}명 (총 ${total}명)"
        } else if (first != null) {
            "${first.grade}학년 ${first.classNum}반  성명: ${first.name} (총 1명)"
        } else {
            "-"
        }

        val rows = listOf(
            "봉사 활동 인원" to participantSummary,
            "봉사 활동 기간" to "${formatDate(data.startDate)} ~ ${formatDate(data.endDate)}",
            "활동 내용" to data.description
        )

        val table = doc.createTable(rows.size, 2)

        rows.forEachIndexed { i, (label, value) ->
            setCell(table.getRow(i).getCell(0), label, bold = true)
            setCell(table.getRow(i).getCell(1), value)
        }
    }

    private fun addParticipantsTable(doc: XWPFDocument, participants: List<ParticipantInfo>) {
        doc.createParagraph().apply { spacingBefore = 300 }
            .createRun().apply {
                setText("봉사활동 참여자 명단")
                isBold = true
                fontSize = 12
                fontFamily = FONT
            }

        val headers = listOf("학년", "반", "번호", "이름", "인정시간")
        val table = doc.createTable(participants.size + 1, headers.size)

        headers.forEachIndexed { i, h ->
            setCell(table.getRow(0).getCell(i), h, bold = true, center = true)
        }

        participants.forEachIndexed { rowIdx, p ->
            listOf(
                p.grade.toString(),
                p.classNum.toString(),
                p.num.toString(),
                p.name,
                "${p.recognizedHours}시간"
            ).forEachIndexed { colIdx, v ->
                setCell(table.getRow(rowIdx + 1).getCell(colIdx), v, center = true)
            }
        }
    }

    private fun addFooter(doc: XWPFDocument, teacherName: String) {
        val today = LocalDate.now()

        doc.createParagraph().apply {
            alignment = ParagraphAlignment.RIGHT
            spacingBefore = 400
        }.createRun().apply {
            setText("작성일: ${today.year}년 ${today.monthValue}월 ${today.dayOfMonth}일")
            fontSize = 10
            fontFamily = FONT
        }

        doc.createParagraph().apply {
            alignment = ParagraphAlignment.RIGHT
        }.createRun().apply {
            setText("확인자  교사: $teacherName")
            fontSize = 10
            fontFamily = FONT
        }
    }

    private fun setCell(
        cell: XWPFTableCell,
        text: String,
        bold: Boolean = false,
        center: Boolean = false
    ) {
        cell.removeParagraph(0)
        val para = cell.addParagraph()

        if (center) {
            para.alignment = ParagraphAlignment.CENTER
        }

        para.createRun().apply {
            setText(text)
            isBold = bold
            fontSize = 10
            fontFamily = FONT
        }
    }

    private fun formatDate(date: LocalDate) =
        "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"

    companion object {
        private val A4_WIDTH = BigInteger.valueOf(11906)
        private val A4_HEIGHT = BigInteger.valueOf(16838)
        private val MARGIN = BigInteger.valueOf(1800)

        private const val FONT = "맑은 고딕"
    }
}
