package com.bridge.community.controller

import com.bridge.community.domain.model.MemberFeatureRecommend
import com.bridge.community.domain.repository.NextFeatureRepository
import com.bridge.community.domain.repository.MemberFeatureRecommendRepository
import com.bridge.community.domain.repository.getMemberFeatureRecommendById
import com.bridge.community.domain.repository.getMemberFeatureRecommendByIds
import com.bridge.community.dto.CreateNextFeatureDto
import com.bridge.community.dto.DeleteNextFeatureDto
import com.bridge.community.dto.NextFeatureDto
import com.bridge.community.exception.NewFeatureNotFoundException
import com.bridge.community.exception.NewFeatureRecommendNotPermissionException
import com.bridge.community.exception.RecommendNotFoundException
import com.bridge.community.utils.JwtGenerator
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes

@RestController
@RequestMapping("/features")
class FeatureController(
    private val nextFeatureRepository: NextFeatureRepository,
    private val memberFeatureRecommendRepository: MemberFeatureRecommendRepository,
    private val jwtGenerator: JwtGenerator,
) {

    @Transactional(readOnly = true)
    @GetMapping
    fun getNextFeatures(): List<NextFeatureDto> {
        val request = (currentRequestAttributes() as ServletRequestAttributes).request

        return if (request.getHeader("ACCESS-TOKEN").isNullOrEmpty()) {
            nextFeatureRepository.findAll().map { NextFeatureDto((it)) }
        } else {
            val memberId = jwtGenerator.getMemberInfoByJwt()
            val memberFeatureRecommends = memberFeatureRecommendRepository.getMemberFeatureRecommendById(memberId)

            val featureRecommends = HashMap<Long, Boolean>()
            memberFeatureRecommends.forEach { featureRecommends[it.featureId] = it.recommend }

            nextFeatureRepository.findAll().map { NextFeatureDto(it, featureRecommends) }
        }
    }

    @Transactional
    @PostMapping
    fun createNextFeatureRecommend(
        @RequestBody createNextFeatureDto: CreateNextFeatureDto
    ) {
        val request = (currentRequestAttributes() as ServletRequestAttributes).request

        if (request.getHeader("ACCESS-TOKEN").isNullOrEmpty()) {
            throw NewFeatureRecommendNotPermissionException()
        } else {
            val memberId = jwtGenerator.getMemberInfoByJwt()
            val memberFeatureRecommend = memberFeatureRecommendRepository.getMemberFeatureRecommendByIds(memberId, createNextFeatureDto.featureId)
            val feature = nextFeatureRepository.findById(createNextFeatureDto.featureId).orElseThrow { NewFeatureNotFoundException() }

            if (memberFeatureRecommend == null) {
                feature.increaseRecommendCount(createNextFeatureDto.recommend)
                memberFeatureRecommendRepository.save(
                    MemberFeatureRecommend(
                        memberId = memberId,
                        featureId = createNextFeatureDto.featureId,
                        recommend = createNextFeatureDto.recommend
                    )
                )
            } else {
                feature.decreaseRecommendCount(memberFeatureRecommend.recommend)

                memberFeatureRecommend.recommend = createNextFeatureDto.recommend
                feature.increaseRecommendCount(createNextFeatureDto.recommend)

                memberFeatureRecommendRepository.save(memberFeatureRecommend)
            }
        }
    }

    @Transactional
    @DeleteMapping
    fun deleteFeatureRecommend(
        @RequestBody deleteNextFeatureDto: DeleteNextFeatureDto
    ) {
        val feature = nextFeatureRepository.findById(deleteNextFeatureDto.featureId).orElseThrow { NewFeatureNotFoundException() }
        val memberId = jwtGenerator.getMemberInfoByJwt()
        val memberFeatureRecommend = memberFeatureRecommendRepository.getMemberFeatureRecommendByIds(memberId, feature.featureId) ?: throw RecommendNotFoundException()

        feature.decreaseRecommendCount(memberFeatureRecommend.recommend)
        nextFeatureRepository.save(feature)
        memberFeatureRecommendRepository.delete(memberFeatureRecommend)
    }
}