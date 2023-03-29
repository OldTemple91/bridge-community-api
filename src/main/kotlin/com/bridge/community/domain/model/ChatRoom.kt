package com.bridge.community.domain.model

import javax.persistence.*

@Entity
@Table(name = "bak_chat_room")
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val chatRoomId : Long = -1,

    @Column(name = "room_name")
    val roomName: String,
) {
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    var chatMessage: MutableList<ChatMessage> = arrayListOf()
}
