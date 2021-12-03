package com.axis.parkingcloudgateway.config

import com.axis.parkingcloudgateway.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SpringCloudConfig {
    @Autowired
    private val filter: JwtAuthenticationFilter? = null

    @Bean
    open fun routes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route("parking") { r: PredicateSpec -> r.path("/parking/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://localhost:8081/") }
                .route("booking") { r: PredicateSpec -> r.path("/booking/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://localhost:8082/") }
                .route("parking-auth") { r: PredicateSpec -> r.path("/parking-auth/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://localhost:8083/") }.build()
    }
//    @Bean
//    fun kotlinBasedRoutes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
//            routeLocatorBuilder.routes {
//                route {
//                    path("/parking/**")
//                    filters {
//                            filter
//                            stripPrefix(0)
//                    }
//                    uri("http://localhost:8081/")
//                }
//                route {
//                    path("/booking/**")
//                    filters {
//                            filter
//                             stripPrefix(0)
//                    }
//                    uri("http://localhost:8082/")
//                }
//            }
}