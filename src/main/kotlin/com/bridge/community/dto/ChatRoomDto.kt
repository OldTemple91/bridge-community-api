package com.bridge.community.dto

import com.bridge.community.domain.model.ChatRoom

data class ChatRoomDto(
    val chatRoomId: Long,
    val roomName: String,
) {
    constructor(chatRoom: ChatRoom): this(
        chatRoomId = chatRoom.chatRoomId,
        roomName = chatRoom.roomName,
    )
}

data class ChatRoomCreateDto(
    val roomName: String,
)