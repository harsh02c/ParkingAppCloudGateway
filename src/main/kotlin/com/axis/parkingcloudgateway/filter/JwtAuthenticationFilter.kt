package com.axis.parkingcloudgateway.filter

import com.axis.parkingcloudgateway.exception.JwtTokenMalformedException
import com.axis.parkingcloudgateway.exception.JwtTokenMissingException
import com.axis.parkingcloudgateway.util.JwtUtil
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Predicate


@Component
class JwtAuthenticationFilter : GatewayFilter {
    @Autowired
    private val jwtUtil: JwtUtil? = null
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

            val request = exchange.request

//        val apiEndpoints: List<String> = java.util.List.of("/register", "/login")
        val apiEndpoints: List<String> =  listOf("/register", "/login")

        val isApiSecured = Predicate { r: ServerHttpRequest ->
            apiEndpoints.stream()
                    .noneMatch { uri: String? -> r.uri.path.contains(uri!!) }
        }

        if (isApiSecured.test(request)) {
            if (!request.headers.containsKey("Authorization")) {
                val response = exchange.response
                response.statusCode = HttpStatus.UNAUTHORIZED
//                return response.setComplete()
                return exchange.response.writeWith(Flux.just(DefaultDataBufferFactory().wrap("Unauthorized"?.toByteArray()!!)))

            }

            val token = request.headers.getOrEmpty("Authorization")[0]


            try {
                jwtUtil?.validateToken(token)!!
            } catch (e: JwtTokenMalformedException) {
                // e.printStackTrace();
                println(e.message)
                val response = exchange.response
                response.statusCode = HttpStatus.BAD_REQUEST
//                return response.setComplete()
                //Send response as text
                return exchange.response.writeWith(Flux.just(DefaultDataBufferFactory().wrap(e.message?.toByteArray()!!)))


            } catch (e: JwtTokenMissingException) {
                println(e.message)
                val response = exchange.response
                response.statusCode = HttpStatus.BAD_REQUEST
//                return response.setComplete()
                //Send response as text
                return exchange.response.writeWith(Flux.just(DefaultDataBufferFactory().wrap(e.message?.toByteArray()!!)))

            }
            val claims: Claims = jwtUtil?.getClaims(token)!!
            exchange.request.mutate().header("id", claims["id"].toString()).build()
        }
        return chain.filter(exchange)
    }
}