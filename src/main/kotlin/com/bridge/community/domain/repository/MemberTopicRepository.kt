package com.bridge.community.domain.repository

import com.bridge.community.domain.model.MemberTopic
import com.bridge.community.domain.model.QMemberTopic.memberTopic
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface MemberTopicRepository: JpaRepository<MemberTopic, MemberTopic.MemberTopicId>, QuerydslPredicateExecutor<MemberTopic> {
    override fun findAll(predicate: Predicate): List<MemberTopic>

}

fun MemberTopicRepository.getMemberTopicById(memberId: Long): List<MemberTopic> {
    return findAll(memberTopic.memberId.eq(memberId))
}

fun MemberTopicRepository.getMemberTopicByIds(memberId: Long, topicId: Long): List<MemberTopic> {
    return findAll(memberTopic.memberId.eq(memberId).and(memberTopic.topicId.`in`(topicId)))
}
