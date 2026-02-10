package finda.findaauth.domain.student.vo

class StudentNumber(
    val grade: Int,
    val classNum: Int,
    val num: Int
) {

    companion object {
        fun parse(value: String): ParsedStudentInfo {
            val trimmed = value.trim()
            val parts = trimmed.split(Regex("\\s+"))

            require(parts.size == 2) { "학생 정보 형식은 '학번 이름'이어야 합니다" }

            val numberPart = parts[0]
            val name = parts[1]

            require(numberPart.length == 4) { "학번은 4자리여야 합니다" }
            require(numberPart.all { it.isDigit() }) { "학번은 숫자만 가능합니다" }

            val grade = numberPart[0].digitToInt()
            val classNum = numberPart[1].digitToInt()
            val num = numberPart.substring(2, 4).toInt()

            require(grade in 1..3) { "학년은 1~3학년만 가능합니다" }
            require(classNum in 1..4) { "반은 1~4반만 가능합니다" }
            require(num in 1..20) { "번호는 01~20 사이여야 합니다" }

            return ParsedStudentInfo(
                studentNumber = StudentNumber(
                    grade = grade,
                    classNum = classNum,
                    num = num
                ),
                name = name
            )
        }
    }
}
