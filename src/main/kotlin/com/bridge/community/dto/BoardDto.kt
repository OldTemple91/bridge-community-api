package com.bridge.community.dto

import com.bridge.community.domain.model.Board
import com.bridge.community.domain.model.BoardReply
import com.bridge.community.domain.model.MemberBoardRecommend
import java.time.LocalDateTime

data class BoardDto(
    val boardId: Long,
    val topicIconUrl: String,
    val topicName: String,
    val title: String,
    //val topicId: Long,
    val nickname: String?,
    val createdAt: LocalDateTime,
    val viewCount: Long,
    val recommendCount: Long,
    val replyCount: Int
) {
    constructor(b: Board) : this(
        boardId = b.boardId,
        topicIconUrl = b.topic.topicIconUrl,
        topicName = b.topic.topicName,
        title = b.title,
        nickname = b.member.nickname,
        createdAt = b.createdAt,
        viewCount = b.viewCount,
        recommendCount = b.recommendCount,
        replyCount = b.boardReplyCount
    )
}

data class BoardContentsDto(
    val boardId: Long,
    val topicIconUrl: String,
    val topicName: String,
    val title: String,
    val nickname: String?,
    val createdAt: LocalDateTime,
    val viewCount: Long,
    val recommendCount: Long,
    val replyCount: Int,
    val contents: String,
    val recommend: Boolean
) {
    constructor(b: Board, recommend: List<MemberBoardRecommend>) : this(
        boardId = b.boardId,
        topicIconUrl = b.topic.topicIconUrl,
        topicName = b.topic.topicName,
        title = b.title,
        nickname = b.member.nickname,
        createdAt = b.createdAt,
        viewCount = b.viewCount,
        recommendCount = b.recommendCount,
        replyCount = b.boardReplyCount,
        contents = b.contents,
        recommend = recommend.isNotEmpty()
    )
}

data class BoardCreateDto(
    val title: String,
    val topicId: Long,
    val contents: String,
)

data class BoardUpdateDto(
    val title: String,
    val contents: String,
)

data class BoardReplyDto(
    val replyId: Long,
    val replyOwner: Boolean,
    val memberId: Long,
    val nickname: String?,
    val createdAt: LocalDateTime,
    val replyContents: String,
    val nestedReply: List<ChildDto>
) {
    constructor(br: BoardReply): this(
        replyId = br.replyId,
        replyOwner = br.memberId == br.board.memberId,
        memberId = br.memberId,
        nickname = br.member.nickname,
        createdAt = br.createdAt,
        replyContents = br.replyContents,
        nestedReply = br.children.map { ChildDto(it) }
    )
}

data class BoardReplyCreateDto(
    val boardId: Long,
    val parentId: Long?,
    val replyContents: String
)

data class ChildDto(
    val replyId: Long,
    val replyOwner: Boolean,
    val memberId: Long,
    val nickname: String?,
    val createdAt: LocalDateTime,
    val parentId: Long?,
    val replyContents: String,
) {
    constructor(br: BoardReply): this(
        replyId = br.replyId,
        replyOwner = br.memberId == br.board.memberId,
        memberId = br.memberId,
        nickname = br.member.nickname,
        createdAt = br.createdAt,
        parentId = br.parentId,
        replyContents = br.replyContents,
    )
}