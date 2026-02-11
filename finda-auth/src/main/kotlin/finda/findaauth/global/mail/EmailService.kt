package finda.findaauth.global.mail

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {
    fun sendVerificationCode(to: String, code: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        helper.setTo(to)
        helper.setSubject("FINDA 이메일 인증 코드 안내")
        helper.setText(createTextContent(code), false)

        mailSender.send(mimeMessage)
    }

    private fun createTextContent(code: String): String {
        return """
            안녕하세요, FINDA입니다.
            
            회원가입을 위한 이메일 인증 코드가 발급되었습니다.
            아래 인증 코드를 입력하여 이메일 인증을 완료해주세요.
            
            인증코드: $code
            
            해당 인증 코드는 발급 시점으로부터 5분간 유효합니다.
            본인이 요청하지 않은 경우 이 메일은 무시하셔도 됩니다.
            
            감사합니다.
            FINDA 드림
            ---
            본 메일은 발신 전용 메일입니다.
        """.trimIndent()
    }
}
