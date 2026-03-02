package finda.findanotification.application.port.`in`.fcm

data class FcmSendResult(
    val successTokens: List<String>,
    val failedTokens: List<String>
)
