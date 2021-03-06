package com.axis.parkingcloudgateway.exception

import javax.naming.AuthenticationException


class JwtTokenMissingException(msg: String?) : AuthenticationException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}