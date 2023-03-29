package com.bridge.community.domain.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "board_reply")
class BoardReply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    val replyId: Long = -1,

    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "parent_id")
    val parentId: Long?,

    @Column(name = "contents")
    val replyContents: String,

    @Column(name = "deleted_yn")
    val deletedYn: Boolean = false,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    lateinit var member: Member

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    lateinit var board: Board

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    lateinit var parent: BoardReply

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    lateinit var children: List<BoardReply>
}