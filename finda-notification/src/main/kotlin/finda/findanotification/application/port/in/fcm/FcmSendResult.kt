package finda.findanotification.application.port.`in`.fcm

data class FcmSendResult(
    val successTokens: List<String>,
    val failedTokens: List<String>
) {
    /**
     * 기본 data class의 toString()은 모든 필드를 그대로 문자열로 출력함
     * FCM 디바이스 토큰은 민감한 식별 정보이므로 로그에 원문이 노출되지 않도록
     * toString()을 오버라이드하여 성공/실패 개수만 반환하도록 함
     */
    override fun toString(): String {
        return "FcmSendResult(success=${successTokens.size}, failed=${failedTokens.size})"
    }
}
