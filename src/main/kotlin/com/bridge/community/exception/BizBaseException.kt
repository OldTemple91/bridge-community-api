package com.bridge.community.exception

open class BizBaseException(
    val status: Int,
    override val message: String
) : RuntimeException() {
    constructor(errorCode: ErrorCode) : this(
        status = errorCode.status,
        message = errorCode.message
    )
}