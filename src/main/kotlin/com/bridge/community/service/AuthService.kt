package com.bridge.community.service

import com.bridge.community.domain.model.AuthCode
import com.bridge.community.domain.repository.AuthRepository
import com.bridge.community.exception.BizBaseException
import com.bridge.community.exception.ErrorCode
import com.bridge.community.utils.MessageClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val messageClient: MessageClient,
    private val authRepository: AuthRepository,
) {

    private val randomCode: String
        get() {
            val rand = Random()
            val length = 6
            val code = StringBuilder()
            while (code.length < length) {
                code.append(rand.nextInt(9))
            }
            return code.toString()
        }

    private fun createMessage(code: String): String {
        return String.format("[인증번호:%s] Bridge 인증번호 입니다", code)
    }

    fun checkPhoneNum(phoneNum: String) {
        val phoneNumExp = "^\\d{3}\\d{3,4}\\d{4}\$"
        if (!phoneNum.matches(phoneNumExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_PHONE_NUM)
    }

    fun parsePhoneNum(phoneNum: String): String {
        return "82" + phoneNum.subSequence(1, 11)
    }

    @Transactional
    fun sendVerifyCode(request: com.bridge.community.controller.MessageCode) {

        checkPhoneNum(request.phoneNumber)

        val phoneNumber = parsePhoneNum(request.phoneNumber)
        val randomCode = randomCode

        messageClient.sendMessage(
            phoneNumber = phoneNumber,
            content = createMessage(randomCode)
        )

        try {

            val authCode = authRepository.findByPhoneNumber(phoneNumber)
            authCode.code = randomCode
            authRepository.save(authCode)

        } catch (_: Exception) {

            authRepository.save(
                AuthCode(
                    phoneNumber = phoneNumber,
                    code = randomCode
                )
            )
        }
    }

    @Transactional
    fun verifyCode(request: com.bridge.community.controller.VerifyCode){
        val authCode = authRepository.findByPhoneNumber(parsePhoneNum(request.phoneNumber))
        if(authCode.code != request.code)
            throw BizBaseException(ErrorCode.INVALID_AUTH_CODE)
    }
}