package com.bridge.community.domain.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "auth_code")
class AuthCode(
    @Id
    val phoneNumber: String,
    var code: String,
)