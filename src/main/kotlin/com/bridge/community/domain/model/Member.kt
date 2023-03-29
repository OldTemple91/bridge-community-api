package com.bridge.community.domain.model

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val memberId: Long = -1L,

    @NotNull
    @Column(name = "password")
    val password: String,

    @NotNull
    @Column(name = "name")
    val name: String,

    @NotNull
    @Column(name = "phone_number")
    val phoneNumber: String,

    @Column(name = "nickname")
    var nickname: String? = null,

    @NotNull
    @Column(name = "agreement1")
    val agreementServiceUsage: Boolean,

    @NotNull
    @Column(name = "agreement2")
    val agreementPersonalInfo: Boolean,

    @NotNull
    @Column(name = "agreement3")
    val agreementMarketing: Boolean,
)