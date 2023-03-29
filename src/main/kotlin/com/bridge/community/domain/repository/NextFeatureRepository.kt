package com.bridge.community.domain.repository

import com.bridge.community.domain.model.NextFeature
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface NextFeatureRepository: JpaRepository<NextFeature, Long>, QuerydslPredicateExecutor<NextFeature> {
}