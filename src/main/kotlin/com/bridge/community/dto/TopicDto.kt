package com.bridge.community.dto

import com.bridge.community.domain.model.Topic


data class TopicDto(
    val topicId: Long,
    val topicName: String,
    val topicExplanation: String,
    val topicIconUrl: String,
    val join: Boolean
) {
    constructor(t: Topic, memberTopics: HashSet<Long>) : this(
        topicId = t.topicId,
        topicName = t.topicName,
        topicExplanation = t.topicExplanation,
        topicIconUrl = t.topicIconUrl,
        join = memberTopics.contains(t.topicId)
    )
    constructor(t: Topic) : this(
        topicId = t.topicId,
        topicName = t.topicName,
        topicExplanation = t.topicExplanation,
        topicIconUrl = t.topicIconUrl,
        join = false
    )

}

data class TopicCreateDto(
    val topicName: String,
    val topicExplanation: String,
    val topicType: String
)