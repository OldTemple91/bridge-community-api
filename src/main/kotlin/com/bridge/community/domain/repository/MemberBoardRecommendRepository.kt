package com.bridge.community.domain.repository

import com.bridge.community.domain.model.MemberBoardRecommend
import com.bridge.community.domain.model.QMemberBoardRecommend.memberBoardRecommend
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface MemberBoardRecommendRepository: JpaRepository<MemberBoardRecommend, MemberBoardRecommend.MemberBoardRecommendId>, QuerydslPredicateExecutor<MemberBoardRecommend> {
    override fun findAll(predicate: Predicate): List<MemberBoardRecommend>
}

fun MemberBoardRecommendRepository.getMemberBoardRecommendByIds(memberId: Long, boardId: Long): List<MemberBoardRecommend> {
    return findAll(memberBoardRecommend.memberId.eq(memberId).and(memberBoardRecommend.boardId.eq(boardId)))
}