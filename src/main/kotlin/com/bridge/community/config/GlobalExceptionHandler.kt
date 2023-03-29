package com.bridge.community.config

import com.bridge.community.exception.BizBaseException
import com.bridge.community.exception.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackages = ["com.bridge.community"])
class GlobalExceptionHandler {

    @ExceptionHandler(BizBaseException::class)
    protected fun handleBaseException(be: BizBaseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(be.status)
            .body(
                ErrorResponse(be)
            )
    }
}