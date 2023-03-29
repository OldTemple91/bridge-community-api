package com.bridge.community.controller

import com.bridge.community.domain.model.ChatMessage
import com.bridge.community.domain.model.ChatRoom
import com.bridge.community.domain.repository.ChatMessageRepository
import com.bridge.community.domain.repository.ChatRoomRepository
import com.bridge.community.dto.*
import com.bridge.community.exception.BizBaseException
import com.bridge.community.exception.ErrorCode
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StompChatController(
    private val template: SimpMessagingTemplate,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository
) {
    @MessageMapping("/chat/enter")
    fun enter(messageDto: ChatRoomEnterDto) {
        chatRoomRepository.findById(messageDto.chatRoomId).orElseThrow { BizBaseException(ErrorCode.RESOURCE_NOT_FOUND) }

        //rabbit 용
        template.convertAndSend("/topic/" + messageDto.chatRoomId, ChatEnterDto(messageDto._userName))
        //stomp 용
        //template.convertAndSend("/sub/chat/room/" + messageDto.chatRoomId, "${messageDto._userName}님이 입장했습니다.")


    }

    @MessageMapping("/chat/message")
    fun message(messageDto: ChatMessageCreateDto) {
        val chatRoom = chatRoomRepository.findById(messageDto.chatRoomId).orElseThrow { BizBaseException(ErrorCode.RESOURCE_NOT_FOUND) }

        //rabbit 용
        template.convertAndSend("/topic/" + messageDto.chatRoomId, messageDto)

        //stomp 용
        //template.convertAndSend("/sub/chat/room/" + messageDto.chatRoomId, messageDto)

        val chat = ChatMessage(
            chatRoomId = chatRoom.chatRoomId,
            userName = messageDto.userName,
            chatMessage = messageDto.chatMessage
        )
        chatMessageRepository.save(chat)

    }

    @PostMapping("/room")
    fun createRoom(
        @RequestBody chatRoomCreateDto: ChatRoomCreateDto) {
        val chatRoom = ChatRoom(
            roomName = "test room"
        )
        chatRoomRepository.save(chatRoom)
    }

    @GetMapping("/room")
    fun getRoom(): List<ChatRoomDto> {
       return chatRoomRepository.findAll().map { ChatRoomDto(it) }
    }

    @GetMapping("/chat/room/{id}")
    fun getRoom(
        @PathVariable id: Long
    ): ChatRoomDto {
        val chatRoom = chatRoomRepository.findById(id).orElseThrow { BizBaseException(ErrorCode.RESOURCE_NOT_FOUND) }
            return ChatRoomDto(chatRoom)
    }
}



