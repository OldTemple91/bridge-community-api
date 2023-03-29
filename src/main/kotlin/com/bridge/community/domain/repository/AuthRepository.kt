package com.bridge.community.domain.repository

import com.bridge.community.domain.model.AuthCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface AuthRepository : JpaRepository<AuthCode, String>, QuerydslPredicateExecutor<AuthCode> {
    fun findByPhoneNumber(phoneNumber: String): AuthCode
}