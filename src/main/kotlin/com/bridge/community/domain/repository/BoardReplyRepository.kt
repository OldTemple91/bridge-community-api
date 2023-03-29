package com.bridge.community.domain.repository

import com.querydsl.core.types.Predicate
import com.bridge.community.domain.model.BoardReply
import com.bridge.community.domain.model.QBoardReply.boardReply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BoardReplyRepository: JpaRepository<BoardReply, Long>, QuerydslPredicateExecutor<BoardReply> {
    override fun findAll(predicate: Predicate): List<BoardReply>
}

fun BoardReplyRepository.getBoardReplyByBoardId(boardId: Long): List<BoardReply> {
    return findAll(boardReply.boardId.eq(boardId).and(boardReply.parentId.isNull))
}