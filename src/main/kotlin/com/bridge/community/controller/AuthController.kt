package com.bridge.community.controller

import com.bridge.community.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/send")
    fun getVerificationCode(
        @RequestBody request: MessageCode,
    ) {
        authService.sendVerifyCode(request)
    }

    @PostMapping("/verify")
    fun verifyCode(
        @RequestBody request: VerifyCode,
    ){
       authService.verifyCode(request)
    }
}

data class MessageCode(
    val phoneNumber: String
)

data class VerifyCode(
    val phoneNumber: String,
    val code: String
)
