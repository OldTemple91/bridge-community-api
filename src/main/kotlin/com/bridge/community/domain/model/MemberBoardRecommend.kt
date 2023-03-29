package com.bridge.community.domain.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Table

@Entity
@Table(name = "member_board_recommend")
@IdClass(MemberBoardRecommend.MemberBoardRecommendId::class)
class MemberBoardRecommend(
    @Id
    @Column(name = "member_id")
    val memberId: Long,

    @Id
    @Column(name = "board_id")
    val boardId: Long
) {
    data class MemberBoardRecommendId(
        var memberId: Long = -1,
        var boardId: Long = -1
    ): java.io.Serializable
}