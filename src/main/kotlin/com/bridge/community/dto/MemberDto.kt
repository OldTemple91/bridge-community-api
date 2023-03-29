package com.bridge.community.dto

data class MemberCreateRequest(
    val phoneNumber: String,
    val name: String,
    var password: String,
    val agreementServiceUsage: Boolean,
    val agreementPersonalInfo: Boolean,
    val agreementMarketing: Boolean,
)

data class LogInRequest(
    val phoneNumber: String,
    val password: String,
)

data class LogInResponse(
    val memberId: Long,
    val name: String,
    val nickname: String?,
    val token: String,
)

data class CreateMemberTopic(
    val topicId: Long
)

data class MemberPhoneNumber(
    val phoneNumber: String
)