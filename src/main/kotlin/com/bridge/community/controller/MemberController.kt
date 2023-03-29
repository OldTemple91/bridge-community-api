package com.bridge.community.controller

import com.bridge.community.dto.*
import com.bridge.community.service.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService,
) {

    @PostMapping
    fun createMember(@RequestBody memberCreateRequest: MemberCreateRequest){
        memberService.createMember(memberCreateRequest)
    }

    @PostMapping("/duplication-checking")
    fun checkDuplicateMember(@RequestBody memberPhoneNumber: MemberPhoneNumber){
        memberService.checkDuplicateMember(memberPhoneNumber)
    }

    @PostMapping("/log-in")
    fun logIn(@RequestBody logInRequest: LogInRequest): LogInResponse{
        return memberService.logIn(logInRequest)
    }

    @PostMapping("/topics")
    fun createMemberTopic(
        @RequestBody topicId: CreateMemberTopic
    ) {
        memberService.createMemberTopic(topicId)
    }

    @DeleteMapping("/topics")
    fun deleteMemberTopic(
        @RequestBody topicId: CreateMemberTopic
    ) {
        memberService.deleteMemberTopic(topicId)
    }
}