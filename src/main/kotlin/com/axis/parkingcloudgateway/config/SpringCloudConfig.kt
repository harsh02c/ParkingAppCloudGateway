package com.axis.parkingcloudgateway.config

import com.axis.parkingcloudgateway.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SpringCloudConfig {
    @Autowired
    private val filter: JwtAuthenticationFilter? = null

//    @Bean
//    open fun routes(builder: RouteLocatorBuilder): RouteLocator? {
//        return builder.routes().route("parking") { r: PredicateSpec -> r.path("/parking/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://parkingapp-env.eba-ij2rwtaf.ap-south-1.elasticbeanstalk.com/") }
//                .route("booking") { r: PredicateSpec -> r.path("/booking/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://parkingappbooking-env.eba-mmaavaa4.ap-south-1.elasticbeanstalk.com/") }
//                .route("parking-auth") { r: PredicateSpec -> r.path("/parking-auth/**").filters { f: GatewayFilterSpec -> f.filter(filter) }.uri("http://parkingapplogin-env.eba-xjq33k2e.ap-south-1.elasticbeanstalk.com/") }.build()
//    }
    @Bean
    open fun routes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route("parking") { r: PredicateSpec -> r.path("/parking/**").filters { f: GatewayFilterSpec -> f.setResponseHeader(
            "Access-Control-Allow-Origin","*");f.filter(filter)}.uri("http://localhost:8081/") }
                .route("booking") { r: PredicateSpec -> r.path("/booking/**").filters { f: GatewayFilterSpec -> f.setResponseHeader(
                    "Access-Control-Allow-Origin","*");f.filter(filter) }.uri("http://localhost:8082/") }
                .route("parking-auth") { r: PredicateSpec -> r.path("/parking-auth/**").filters { f: GatewayFilterSpec -> f.setResponseHeader(
                    "Access-Control-Allow-Origin","*");f.filter(filter) }.uri("http://localhost:8083/") }.build()
    }

//    @Bean
//    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator? {
//        return builder.routes()
//            .route { p: PredicateSpec ->
//                p
//                    .path("/order/**")
//                    .filters { f: GatewayFilterSpec ->
//                        f.setResponseHeader(
//                            "Access-Control-Allow-Origin",
//                            "http://localhost:8081"
//                        );f.filter(filter)
//                    }
//                    .uri("http://localhost:9090")
//            }
//            .route { p: PredicateSpec ->
//                p
//                    .path("/priority-model/selection/**")
//                    .filters { f: GatewayFilterSpec ->
//                        f.addResponseHeader(
//                            "Access-Control-Allow-Origin",
//                            "http://localhost:8081"
//                        )
//                    }
//                    .uri("http://localhost:9090")
//            }
//            .build()
////    }
}