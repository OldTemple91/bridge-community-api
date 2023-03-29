package com.bridge.community.domain.model

import javax.persistence.*

@Entity
@Table(name = "bak_chat_message")
class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    val chatId: Long = -1,

    @Column(name = "chat_room_id")
    val chatRoomId: Long,

    @Column(name = "user_name")
    val userName: String,

    @Column(name = "message")
    val chatMessage: String,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", insertable = false, updatable = false)
    lateinit var chatRoom: ChatRoom
}