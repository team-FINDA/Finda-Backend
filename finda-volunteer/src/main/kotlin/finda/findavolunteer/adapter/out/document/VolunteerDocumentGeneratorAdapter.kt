package finda.findavolunteer.adapter.out.document

import finda.findavolunteer.application.port.out.document.GenerateVolunteerDocumentPort
import finda.findavolunteer.domain.volunteer.data.ParticipantInfo
import finda.findavolunteer.domain.volunteer.data.VolunteerDocumentData
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth
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
        sectPr.addNewPgSz().apply {
            w = A4_WIDTH
            h = A4_HEIGHT
        }
        sectPr.addNewPgMar().apply {
            top = MARGIN; bottom = MARGIN; left = MARGIN; right = MARGIN
        }
    }

    private fun addTitle(doc: XWPFDocument, text: String) {
        doc.createParagraph().apply {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 400
        }.createRun().applyStyle(text, bold = true, size = 18)
    }

    private fun addInfoTable(doc: XWPFDocument, data: VolunteerDocumentData) {
        val first = data.participants.firstOrNull()
        val total = data.participants.size

        val participantSummary = when {
            first != null && total > 1 ->
                "${first.grade}학년 ${first.classNum}반  성명: ${first.name} 외 ${total - 1}명 (총 ${total}명)"
            first != null ->
                "${first.grade}학년 ${first.classNum}반  성명: ${first.name} (총 1명)"
            else -> "-"
        }

        val rows = listOf(
            "봉사 활동 인원" to participantSummary,
            "봉사 활동 기간" to "${formatDate(data.startDate)} ~ ${formatDate(data.endDate)}",
            "활동 내용" to data.description
        )

        val colWidths = listOf(2400L, 5906L)
        val table = doc.createTable(rows.size, 2).applyTableWidth(colWidths)

        rows.forEachIndexed { i, (label, value) ->
            setCell(table.getRow(i).getCell(0), label, bold = true, width = colWidths[0])
            setCell(table.getRow(i).getCell(1), value, width = colWidths[1])
        }
    }

    private fun addParticipantsTable(doc: XWPFDocument, participants: List<ParticipantInfo>) {
        doc.createParagraph().apply { spacingBefore = 300 }
            .createRun().applyStyle("봉사활동 참여자 명단", bold = true, size = 12)

        val headers = listOf("학년", "반", "번호", "이름", "인정시간")
        val colWidths = listOf(1400L, 1400L, 1400L, 2706L, 1400L) // 합계 = 8306

        val table = doc.createTable(participants.size + 1, headers.size).applyTableWidth(colWidths)

        headers.forEachIndexed { i, h ->
            setCell(table.getRow(0).getCell(i), h, bold = true, center = true, width = colWidths[i])
        }

        participants.forEachIndexed { rowIdx, p ->
            listOf(p.grade.toString(), p.classNum.toString(), p.num.toString(), p.name, "${p.recognizedHours}시간")
                .forEachIndexed { colIdx, v ->
                    setCell(table.getRow(rowIdx + 1).getCell(colIdx), v, center = true, width = colWidths[colIdx])
                }
        }
    }

    private fun addFooter(doc: XWPFDocument, teacherName: String) {
        val today = LocalDate.now()

        doc.createParagraph().apply {
            alignment = ParagraphAlignment.RIGHT
            spacingBefore = 400
        }.createRun().applyStyle("작성일: ${today.year}년 ${today.monthValue}월 ${today.dayOfMonth}일", size = 10)

        doc.createParagraph().apply {
            alignment = ParagraphAlignment.RIGHT
        }.createRun().applyStyle("확인자  교사: $teacherName", size = 10)
    }

    private fun XWPFTable.applyTableWidth(colWidths: List<Long>): XWPFTable {
        val tbl = ctTbl
        val tblPr = tbl.tblPr ?: tbl.addNewTblPr()
        val tblW = tblPr.tblW ?: tblPr.addNewTblW()
        tblW.w = BigInteger.valueOf(colWidths.sum())
        tblW.type = STTblWidth.DXA

        val tblGrid = tbl.tblGrid ?: tbl.addNewTblGrid()
        colWidths.forEach { w ->
            tblGrid.addNewGridCol().w = BigInteger.valueOf(w)
        }
        return this
    }

    private fun setCell(
        cell: XWPFTableCell,
        text: String,
        bold: Boolean = false,
        center: Boolean = false,
        width: Long
    ) {
        val tcPr = cell.ctTc.tcPr ?: cell.ctTc.addNewTcPr()
        if (tcPr.isSetTcW) tcPr.unsetTcW()
        tcPr.addNewTcW().apply {
            w = BigInteger.valueOf(width)
            type = STTblWidth.DXA
        }

        cell.removeParagraph(0)
        cell.addParagraph().apply {
            if (center) alignment = ParagraphAlignment.CENTER
            createRun().applyStyle(text, bold = bold, size = 10)
        }
    }

    private fun XWPFRun.applyStyle(text: String, bold: Boolean = false, size: Int = 10) {
        setText(text)
        isBold = bold
        fontSize = size
        setFontFamily(FONT, XWPFRun.FontCharRange.ascii)
        setFontFamily(FONT, XWPFRun.FontCharRange.hAnsi)
        setFontFamily(FONT, XWPFRun.FontCharRange.eastAsia)
        setFontFamily(FONT, XWPFRun.FontCharRange.cs)
    }

    private fun formatDate(date: LocalDate) =
        "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"

    companion object {
        private val A4_WIDTH = BigInteger.valueOf(11906)
        private val A4_HEIGHT = BigInteger.valueOf(16838)
        private val MARGIN = BigInteger.valueOf(1800)
        private const val FONT = "나눔고딕"
    }
}
