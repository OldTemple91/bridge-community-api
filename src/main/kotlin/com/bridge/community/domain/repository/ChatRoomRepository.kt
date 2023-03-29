package com.bridge.community.domain.repository

import com.bridge.community.domain.model.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ChatRoomRepository: JpaRepository<ChatRoom, Long>, QuerydslPredicateExecutor<ChatRoom> {
}