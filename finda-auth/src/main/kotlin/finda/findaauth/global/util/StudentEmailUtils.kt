package finda.findaauth.global.util

object StudentEmailUtils {
    private const val STUDENT_EMAIL_DOMAIN = "@dsm.hs.kr"

    fun toFullEmail(emailOrPrefix: String): String {
        return if (emailOrPrefix.contains("@")) {
            emailOrPrefix
        } else {
            "$emailOrPrefix$STUDENT_EMAIL_DOMAIN"
        }
    }
}
