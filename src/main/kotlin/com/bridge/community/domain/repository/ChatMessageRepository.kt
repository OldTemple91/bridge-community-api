package com.bridge.community.domain.repository

import com.bridge.community.domain.model.ChatMessage
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ChatMessageRepository: JpaRepository<ChatMessage, Long>, QuerydslPredicateExecutor<ChatMessage> {

    override fun findAll(predicate: Predicate): List<ChatMessage>
}

//fun ChatMessageRepository.getChatMessageById(chatRoomId: Long): List<ChatMessage> {
//    return findAll(chatMessage.chatRoom.chatRoomId.eq(chatRoomId))
//}