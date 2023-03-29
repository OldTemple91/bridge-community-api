package com.bridge.community.domain.model

import javax.persistence.*

@Entity
@Table(name = "topic")
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    val topicId: Long = -1,

    @Column(name = "topic_name")
    var topicName: String,

    @Column(name = "topic_explanation")
    var topicExplanation: String,

    @Column(name = "topic_icon_url")
    val topicIconUrl: String,

    ) {
}