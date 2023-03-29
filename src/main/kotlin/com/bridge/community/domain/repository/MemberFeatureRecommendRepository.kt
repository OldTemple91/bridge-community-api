package com.bridge.community.domain.repository

import com.bridge.community.domain.model.MemberFeatureRecommend
import com.bridge.community.domain.model.QMemberFeatureRecommend.memberFeatureRecommend
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface MemberFeatureRecommendRepository: JpaRepository<MemberFeatureRecommend, MemberFeatureRecommend.MemberFeatureRecommendId>, QuerydslPredicateExecutor<MemberFeatureRecommend> {
    override fun findAll(predicate: Predicate): List<MemberFeatureRecommend>
}

fun MemberFeatureRecommendRepository.getMemberFeatureRecommendById(memberId: Long): List<MemberFeatureRecommend> {
    return findAll(memberFeatureRecommend.memberId.eq(memberId))
}

fun MemberFeatureRecommendRepository.getMemberFeatureRecommendByIds(memberId: Long, featureId: Long): MemberFeatureRecommend? {
    return findAll(memberFeatureRecommend.memberId.eq(memberId).and(memberFeatureRecommend.featureId.eq(featureId))).firstOrNull()
}