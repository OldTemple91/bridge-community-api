package com.bridge.community.domain.repository

import com.bridge.community.domain.model.Board
import com.querydsl.jpa.impl.JPAQueryFactory

class BoardRepositoryCustomImpl (
    private val query: JPAQueryFactory
): BoardRepositoryCustom {
    private val board = com.bridge.community.domain.model.QBoard.board
    private val member = com.bridge.community.domain.model.QMember.member
    private val topic = com.bridge.community.domain.model.QTopic.topic


    override fun getBoardList(topicIds: HashSet<Long>): List<Board> {
        return query.selectFrom(board)
            .leftJoin(board.member, member)
            .fetchJoin()
            .leftJoin(board.topic, topic)
            .fetchJoin()
            .where(board.topicId.`in`(topicIds))
            .orderBy(board.createdAt.desc())
            .fetch()
    }

    override fun getBoardContentFromTopic(boardId: Long):Board {
        return query.selectFrom(board)
            .leftJoin(board.member, member)
            .fetchJoin()
            .leftJoin(board.topic, topic)
            .fetchJoin()
            .where(board.boardId.eq(boardId))
            .fetchFirst()
    }

}