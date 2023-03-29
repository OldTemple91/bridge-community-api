package com.bridge.community.service

import com.bridge.community.domain.model.*
import com.bridge.community.domain.repository.*
import com.bridge.community.dto.*
import com.bridge.community.exception.*
import com.bridge.community.utils.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MemberService(
    private val jwtGenerator: JwtGenerator,
    private val memberRepository: MemberRepository,
    private val memberTopicRepository: MemberTopicRepository,
    private val topicRepository: TopicRepository
) {

    @Transactional
    fun checkCreateRequest(memberCreateRequest: MemberCreateRequest) {
        val phoneNumberExp = "^\\d{3}\\d{3,4}\\d{4}\$"
        val nameExp = "^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{1,8}$"
        val passwordExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"

        if (!memberCreateRequest.phoneNumber.matches(phoneNumberExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_PHONE_NUM)
        else if (!memberCreateRequest.name.matches(nameExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_NAME)
        else if (!memberCreateRequest.password.matches(passwordExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_PASSWORD)
    }

    @Transactional
    fun checkLogInRequest(logInRequest: LogInRequest) {
        val phoneNumberExp = "^\\d{3}\\d{3,4}\\d{4}\$"
        val passwordExp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"

        if (!logInRequest.phoneNumber.matches(phoneNumberExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_PHONE_NUM)
        else if (!logInRequest.password.matches(passwordExp.toRegex()))
            throw BizBaseException(ErrorCode.INVALID_PASSWORD)
    }

    @Transactional
    fun checkDuplicateMember(memberPhoneNumber: MemberPhoneNumber) {
        val member = memberRepository.findByPhoneNumber(memberPhoneNumber.phoneNumber)
        if (!member.isEmpty) {
            throw BizBaseException(ErrorCode.MEMBER_ALREADY_EXIST)
        }
    }

    @Transactional
    fun createMember(memberCreateRequest: MemberCreateRequest) {

        checkCreateRequest(memberCreateRequest)

        try {
            val password: String = AES128(Secret.MEMBER_PASSWORD_KEY).encrypt(memberCreateRequest.password)
            memberCreateRequest.password = password

            val member = Member(
                phoneNumber = memberCreateRequest.phoneNumber,
                name = memberCreateRequest.name,
                password = memberCreateRequest.password,
                agreementServiceUsage = memberCreateRequest.agreementServiceUsage,
                agreementPersonalInfo = memberCreateRequest.agreementPersonalInfo,
                agreementMarketing = memberCreateRequest.agreementMarketing
            )
            member.nickname = randomString()
            memberRepository.saveAndFlush(member)

            val createdMember = memberRepository.findByPhoneNumber(memberCreateRequest.phoneNumber)

            val memberId = createdMember.get().memberId
            val topics = topicRepository.findAll()
            val memberTopics = mutableListOf<MemberTopic>()
            for (i in 0 until 5) {
                memberTopics.add(
                    MemberTopic(
                        memberId = memberId,
                        topicId = topics[i].topicId
                    )
                )
            }
            memberTopicRepository.saveAll(memberTopics)

        } catch (ignored: Exception) {
            throw BizBaseException(ErrorCode.PASSWORD_ENCRYPTION_ERROR)
        }
    }

    @Transactional
    fun logIn(logInRequest: LogInRequest): LogInResponse {

        checkLogInRequest(logInRequest)

        val member: Member = memberRepository.findByPhoneNumber(logInRequest.phoneNumber)
            .orElseThrow { BizBaseException(ErrorCode.MEMBER_NOT_FOUND) }

        val password: String = try {
            AES128(Secret.MEMBER_PASSWORD_KEY).decrypt(member.password)
        } catch (ignored: java.lang.Exception) {
            throw BizBaseException(ErrorCode.PASSWORD_DECRYPTION_ERROR)
        }

        return if (password == logInRequest.password) {
            val memberId: Long = member.memberId
            val jwt: String = jwtGenerator.createJwt(memberId)
            LogInResponse(
                memberId = memberId,
                name = member.name,
                nickname = member.nickname,
                token = jwt,
            )

        } else {
            throw BizBaseException(ErrorCode.FAILED_TO_LOGIN)
        }
    }

    /**
     * 닉네임 랜덤 생성
     */
    fun randomString(): String {
        val characterCode = StringBuilder()
        val numberCode = StringBuilder()

        for (i in 0 until 2) {
            characterCode.append(('A' + Random().nextInt('Z' - 'A')))
        }

        for (i in 0 until 4) {
            numberCode.append(Random().nextInt(10))
        }
        return characterCode.toString() + numberCode.toString()
    }

    @Transactional
    fun createMemberTopic(createMemberTopic: CreateMemberTopic) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        memberRepository.findById(memberId).orElseThrow { MemberNotFoundException() }

        val checkTopicId = memberTopicRepository.getMemberTopicByIds(memberId, createMemberTopic.topicId)
        if(checkTopicId.isNotEmpty()) {
            throw MemberTopicDuplicateException()
        }

        val memberTopic = MemberTopic(
            memberId = memberId,
            topicId = createMemberTopic.topicId
        )

        memberTopicRepository.save(memberTopic)
    }

    @Transactional
    fun deleteMemberTopic(createMemberTopic: CreateMemberTopic) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        memberRepository.findById(memberId).orElseThrow { MemberNotFoundException() }

        val checkTopicId = memberTopicRepository.getMemberTopicByIds(memberId, createMemberTopic.topicId)
        memberTopicRepository.delete(checkTopicId[0])
    }
}

