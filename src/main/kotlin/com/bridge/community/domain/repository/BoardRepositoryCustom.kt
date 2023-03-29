package com.bridge.community.domain.repository

import com.bridge.community.domain.model.Board

interface BoardRepositoryCustom {
    fun getBoardList(topicIds: HashSet<Long>): List<Board>

    fun getBoardContentFromTopic(boardId: Long): Board
}