package com.bridge.community.domain.repository

import com.bridge.community.domain.model.Board
import com.bridge.community.domain.model.QBoard.board
import com.bridge.community.domain.model.QTopic.topic
import com.bridge.community.dto.BoardDto
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BoardRepository: JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {
    override fun findAll(predicate: Predicate): List<Board>
}
fun BoardRepository.getBoard(): List<BoardDto> {
    return findAll(board.createdAt.desc()).map { BoardDto(it) }
}

fun BoardRepository.getBoardFromTopic(topicId: Long): List<BoardDto> {
    return findAll(topic.topicId.eq(topicId), board.createdAt.desc()).map { BoardDto(it) }
}