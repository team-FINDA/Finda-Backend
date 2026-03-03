package finda.findagateway.global.config

import finda.findagateway.global.config.properties.ServicesProperties
import finda.findagateway.global.filter.PassportGatewayFilterFactory
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig(
    private val passportGatewayFilterFactory: PassportGatewayFilterFactory,
    private val servicesProperties: ServicesProperties
) {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("finda-auth") { r ->
                r.path("/finda-auth/**")
                    .filters { f ->
                        f.filter(
                            passportGatewayFilterFactory.apply(
                                PassportGatewayFilterFactory.Config()
                            )
                        )
                    }
                    .uri(servicesProperties.authUrl)
            }
            .route("finda-volunteer") { r ->
                r.path("/finda-volunteer/**")
                    .filters { f ->
                        f.filter(
                            passportGatewayFilterFactory.apply(
                                PassportGatewayFilterFactory.Config()
                            )
                        )
                    }
                    .uri(servicesProperties.volunteerUrl)
            }
            .route("finda-notification") { r ->
                r.path("/finda-notification/**")
                    .filters { f ->
                        f.filter(
                            passportGatewayFilterFactory.apply(
                                PassportGatewayFilterFactory.Config()
                            )
                        )
                    }
                    .uri(servicesProperties.notificationUrl)
            }
            .build()
    }
}
