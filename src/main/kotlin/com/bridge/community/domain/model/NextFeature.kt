package com.bridge.community.domain.model

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@Table(name = "bridge_next_feature")
@DynamicUpdate
class NextFeature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    val featureId: Long = -1,

    @Column(name = "title")
    var title: String,

    @Column(name = "contents")
    var contents: String,

    @Column(name = "recommend_count")
    var recommendCount: Long,

    @Column(name = "non_recommend_count")
    var nonRecommendCount: Long,
) {
    fun increaseRecommendCount(recommend: Boolean) {
        when (recommend) {
            true -> this.recommendCount += 1
            false -> this.nonRecommendCount += 1
        }
    }

    fun decreaseRecommendCount(recommend: Boolean) {
        when (recommend) {
            true -> this.recommendCount -= 1
            false -> this.nonRecommendCount -= 1
        }
    }
}