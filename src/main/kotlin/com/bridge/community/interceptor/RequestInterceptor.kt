package com.bridge.community.interceptor

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestInterceptor : HandlerInterceptor {

    companion object {
        const val START_TIME_ATTR_NAME = "startTime"
    }

    private val log = LoggerFactory.getLogger(RequestInterceptor::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        MDC.put("requestId", getRandomString())

        var header = ""
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement()
            val headerValue = request.getHeader(headerName)
            header += ("$headerName : $headerValue ")
        }

        log.info("[REQUEST_IP] method_${request.method} uri_${request.requestURI} remoteHost_${request.remoteHost}")

        val startTime = System.currentTimeMillis()
        request.setAttribute(START_TIME_ATTR_NAME, startTime)
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        MDC.clear()

        val startTime = request.getAttribute(START_TIME_ATTR_NAME) as Long
        val endTime = System.currentTimeMillis()
        val executionTime = endTime - startTime

        log.info("[REQUEST_COMPLETE] method_${request.method} uri_${request.requestURI} status_${response.status} executionTime_${executionTime}ms")
    }

    fun getRandomString(strLength: Int = 8): String {
        val leftLimit = 97 // letter 'a'
        val rightLimit = 122 // letter 'z'

        val random = Random()

        return random.ints(leftLimit, rightLimit + 1)
            .limit(strLength.toLong())
            .collect({ StringBuilder() }, java.lang.StringBuilder::appendCodePoint, java.lang.StringBuilder::append)
            .toString()
    }
}