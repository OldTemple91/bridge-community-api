package com.bridge.community.domain.repository

import com.bridge.community.domain.model.Topic
import com.querydsl.core.types.Predicate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface TopicRepository: JpaRepository<Topic, Long>, QuerydslPredicateExecutor<Topic> {
    override fun findAll(predicate: Predicate): List<Topic>


}