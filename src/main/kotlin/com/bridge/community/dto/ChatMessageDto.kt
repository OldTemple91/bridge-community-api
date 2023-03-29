package com.bridge.community.dto

import com.bridge.community.domain.model.ChatMessage
import com.fasterxml.jackson.annotation.JsonProperty

data class ChatMessageDto(
    val chatId: Long,
    val chatRoomId: Long,
    val userName: String,
    val chatMessage: String
) {
    constructor(chatMessage: ChatMessage): this(
        chatId = chatMessage.chatId,
        chatRoomId = chatMessage.chatRoomId,
        userName = chatMessage.userName,
        chatMessage = chatMessage.chatMessage
    )
}

data class ChatRoomEnterDto(
    val chatRoomId: Long,
    @JsonProperty("userName")
    val _userName: String,
) {
//    val userName: String
//        get() = "$_userName 님이 입장했습니다."
}

data class ChatMessageCreateDto(
    val chatRoomId: Long,
    @JsonProperty("userName")
    val userName: String,
    val chatMessage: String
)

data class ChatEnterDto(
    val userName: String
    ) {
    val message = "${userName}님이 입장했습니다."
}
