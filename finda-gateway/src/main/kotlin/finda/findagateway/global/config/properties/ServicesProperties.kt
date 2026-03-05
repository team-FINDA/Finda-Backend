package finda.findagateway.global.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.services")
class ServicesProperties(
    volunteer: String,
    notification: String,
    auth: String
) {
    val volunteerUrl: String = volunteer
    val notificationUrl: String = notification
    val authUrl: String = auth
}
