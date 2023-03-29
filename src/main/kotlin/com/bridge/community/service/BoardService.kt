package com.bridge.community.service

import com.bridge.community.domain.model.Board
import com.bridge.community.domain.model.BoardReply
import com.bridge.community.domain.model.MemberBoardRecommend
import com.bridge.community.domain.repository.*
import com.bridge.community.dto.*
import com.bridge.community.exception.*
import com.bridge.community.utils.JwtGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime

@Service
@Transactional
class BoardService(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository,
    private val topicRepository: TopicRepository,
    private val boardReplyRepository: BoardReplyRepository,
    private val memberTopicRepository: MemberTopicRepository,
    private val memberBoardRecommendRepository: MemberBoardRecommendRepository,
    private val jwtGenerator: JwtGenerator,
) {
    fun getBoards(): List<BoardDto> {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        return if (request.getHeader("ACCESS-TOKEN").isNullOrEmpty()) {
            boardRepository.getBoard()
        } else {
            val memberId = jwtGenerator.getMemberInfoByJwt()
            val topicIds = HashSet<Long>()
            val memberTopics = memberTopicRepository.getMemberTopicById(memberId)
            memberTopics.forEach { topicIds.add(it.topicId) }

            boardRepository.getBoardList(topicIds).map { BoardDto(it) }
        }
    }

    fun getBoardsFromTopic(topicId: Long): List<BoardDto> {
        return boardRepository.getBoardFromTopic(topicId)
    }

    fun getBoardById(boardId: Long): BoardContentsDto {
        val board = boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }
        val topicId = board.topicId
        val memberId = jwtGenerator.getMemberInfoByJwt()

        val checkTopicId = memberTopicRepository.getMemberTopicByIds(memberId, topicId)
        if(checkTopicId.isEmpty()) {
            throw MemberNotPermissionException()
        }

        board.increaseViewCount()
        boardRepository.save(board)

        val recommend = memberBoardRecommendRepository.getMemberBoardRecommendByIds(memberId, boardId)

        return BoardContentsDto(boardRepository.getBoardContentFromTopic(boardId), recommend)
    }

    fun createBoard(boardCreateDto: BoardCreateDto) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        val member = memberRepository.findById(memberId).orElseThrow { MemberNotFoundException() }
        val topic = topicRepository.findById(boardCreateDto.topicId).orElseThrow { TopicNotFoundException() }

        val checkTopicId = memberTopicRepository.getMemberTopicByIds(member.memberId, topic.topicId)
        if(checkTopicId.isEmpty()) {
            throw MemberNotPermissionException()
        }

        val board = Board(
            memberId = member.memberId,
            title =  boardCreateDto.title,
            topicId = boardCreateDto.topicId,
            contents = boardCreateDto.contents,
            viewCount = 0,
            recommendCount = 0,
            deletedYn = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            boardReplyCount = 0
        )

        boardRepository.save(board)
    }

    fun updateBoardById(boardId: Long, boardUpdateDto: BoardUpdateDto) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        val board = boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }

        if(board.memberId != memberId) {
            throw BoardNotModifyException()
        }

        board.updateBoard(boardUpdateDto.title, boardUpdateDto.contents)
        boardRepository.save(board)
    }

    fun deleteBoardById(boardId: Long) {
        val board = boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }
        val memberId = jwtGenerator.getMemberInfoByJwt()

        if(board.memberId != memberId) {
            throw MemberNotPermissionException()
        }

        boardRepository.delete(board)
    }

    fun createBoardRecommend(boardId: Long) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        val board = boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }
        val recommend = memberBoardRecommendRepository.getMemberBoardRecommendByIds(memberId, boardId)
        if(recommend.isNotEmpty()) {
            throw BoardDuplicateException()
        }
        board.increaseRecommendCount()
        boardRepository.save(board)

        val memberBoardRecommend = MemberBoardRecommend(
            memberId = memberId,
            boardId = boardId
        )
        memberBoardRecommendRepository.save(memberBoardRecommend)
    }

    fun deleteBoardRecommend(boardId: Long) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        val board = boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }
        val recommend = memberBoardRecommendRepository.getMemberBoardRecommendByIds(memberId, boardId)
        if(recommend.isEmpty()) {
            throw RecommendNotFoundException()
        }
        board.decreaseRecommendCount()
        boardRepository.save(board)

        memberBoardRecommendRepository.delete(recommend[0])
    }

    fun getBoardReply(boardId: Long): List<BoardReplyDto> {
        boardRepository.findById(boardId).orElseThrow { BoardNotFoundException() }
        return boardReplyRepository.getBoardReplyByBoardId(boardId).map { BoardReplyDto(it) }
    }

    fun createBoardReply(boardReplyCreateDto: BoardReplyCreateDto) {
        val memberId = jwtGenerator.getMemberInfoByJwt()
        memberRepository.findById(memberId).orElseThrow { MemberNotFoundException() }
        boardRepository.findById(boardReplyCreateDto.boardId).orElseThrow { BoardNotFoundException() }

        val boardReply = BoardReply(
            boardId = boardReplyCreateDto.boardId,
            memberId = memberId,
            parentId = boardReplyCreateDto.parentId,
            replyContents = boardReplyCreateDto.replyContents,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        boardReplyRepository.save(boardReply)
    }

    fun deleteBoardReplyById(replyId: Long) {
        val reply = boardReplyRepository.findById(replyId).orElseThrow { ReplyNotFoundException() }
        val memberId = jwtGenerator.getMemberInfoByJwt()

        if(reply.memberId != memberId) {
            throw ReplyNotModifyException()
        }

        boardReplyRepository.delete(reply)
    }
}