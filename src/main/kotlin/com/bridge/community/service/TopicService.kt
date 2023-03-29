package com.bridge.community.service

import com.bridge.community.domain.model.Topic
import com.bridge.community.domain.repository.MemberTopicRepository
import com.bridge.community.domain.repository.TopicRepository
import com.bridge.community.domain.repository.getMemberTopicById
import com.bridge.community.dto.TopicCreateDto
import com.bridge.community.dto.TopicDto
import com.bridge.community.exception.TopicNotFoundException
import com.bridge.community.utils.JwtGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
@Transactional
class TopicService(
    private val topicRepository: TopicRepository,
    private val jwtGenerator: JwtGenerator,
    private val memberTopicRepository: MemberTopicRepository
) {

    fun getTopics(): List<TopicDto> {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        return if(request.getHeader("ACCESS-TOKEN").isNullOrEmpty()) {
            topicRepository.findAll().map { TopicDto(it) }
        } else {
            val memberId = jwtGenerator.getMemberInfoByJwt()
            val topicIds = HashSet<Long>()

            val memberTopics = memberTopicRepository.getMemberTopicById(memberId)
            memberTopics.forEach { topicIds.add(it.topicId) }

            topicRepository.findAll().map { TopicDto(it, topicIds) }
        }
    }

    fun createTopic(topicCreateDto: TopicCreateDto) {
        val topic = Topic(
            topicName = topicCreateDto.topicName,
            topicExplanation = topicCreateDto.topicExplanation,
            topicIconUrl = topicCreateDto.topicType
        )

        topicRepository.save(topic)
    }

    fun deleteTopic(topicId: Long) {
        topicRepository.findById(topicId).orElseThrow { TopicNotFoundException() }
        topicRepository.deleteById(topicId)
    }
}