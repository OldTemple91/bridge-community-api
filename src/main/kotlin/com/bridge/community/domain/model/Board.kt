package com.bridge.community.domain.model

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Formula
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicUpdate
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    val boardId: Long = -1,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "title")
    var title: String,

    @Column(name = "topic_id")
    val topicId: Long,

    @Column(name = "contents")
    var contents: String,

    @Column(name = "view_count")
    var viewCount: Long,

    @Column(name = "recommend_count")
    var recommendCount: Long,

    @Column(name = "deleted_yn")
    val deletedYn: Boolean,

    @Formula("(select count(1) from board_reply br where br.board_id=board_id)")
    val boardReplyCount: Int,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime


) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    lateinit var member: Member

    @OneToOne
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    lateinit var topic: Topic

    @OneToMany(mappedBy = "board",  cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var boardReplys: MutableList<BoardReply> = arrayListOf()

    fun increaseViewCount() {
        this.viewCount += 1
    }

    fun updateBoard(title: String, contents: String) {
        this.title = title
        this.contents = contents
    }

    fun increaseRecommendCount() {
        this.recommendCount += 1
    }

    fun decreaseRecommendCount() {
        this.recommendCount -= 1
    }
}