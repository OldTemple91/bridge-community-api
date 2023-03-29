package com.bridge.community.domain.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@Entity
@Table(name = "member_topic")
@IdClass(MemberTopic.MemberTopicId::class)
class MemberTopic(
    @Id
    @Column(name = "member_id")
    val memberId: Long,

    @Id
    @Column(name = "topic_id")
    val topicId: Long
) {
    data class MemberTopicId(
        var memberId: Long = -1,
        var topicId: Long = -1
    ): java.io.Serializable

}