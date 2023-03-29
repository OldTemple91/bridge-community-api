package com.bridge.community.domain.repository

import com.bridge.community.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository: JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>{
    fun findByPhoneNumber(phoneNumber: String): Optional<Member>
}