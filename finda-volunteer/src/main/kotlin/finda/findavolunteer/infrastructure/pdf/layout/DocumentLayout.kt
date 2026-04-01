package finda.findavolunteer.infrastructure.pdf.layout

import java.awt.Color

object DocumentLayout {
    // 여백 / 크기
    val margin               = 43f
    val labelW               = 60f
    val stdRowH              = 33f
    val participantRowH      = 23f
    val pColWidths           = floatArrayOf(28f, 45.3f, 45.3f, 45.3f, 45.3f, 45.3f)

    // 제목
    val titleBottomPadding   = 10f
    val titleLineHeightRatio = 1.8f

    // 푸터 텍스트 수직 위치 비율
    val footerLine1Ratio     = 0.28f
    val footerLine2Ratio     = 0.56f
    val footerLine3Ratio     = 0.80f

    // 폰트 크기
    val fontSizeSection      = 10f
    val fontSizeFooter       = 12f
    val fontSizeSubTitle   = 12f
    val fontSizeTitle        = 16f

    // 색상
    val labelBgColor         = Color(0xF2, 0xF2, 0xF2)
    val textColor            = Color.BLACK

    // 선
    val strokeWidth          = 0.7f
}
