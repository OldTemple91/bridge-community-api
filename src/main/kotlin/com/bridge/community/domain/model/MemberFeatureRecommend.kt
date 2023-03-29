package com.bridge.community.domain.model

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@Table(name = "member_feature_recommend")
@IdClass(MemberFeatureRecommend.MemberFeatureRecommendId::class)
@DynamicUpdate
class MemberFeatureRecommend(
    @Id
    @Column(name = "member_id")
    val memberId: Long,

    @Id
    @Column(name = "feature_id")
    val featureId: Long,

    @Column(name = "recommend_yn")
    var recommend: Boolean
) {
    data class MemberFeatureRecommendId(
        var memberId: Long = -1,
        var featureId: Long = -1
    ): java.io.Serializable

}