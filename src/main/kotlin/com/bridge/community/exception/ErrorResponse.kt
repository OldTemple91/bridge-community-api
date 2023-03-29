package com.bridge.community.exception


data class ErrorResponse(
    val status: Int,
    val message: String
) {
    constructor(ex: BizBaseException) : this(
        status = ex.status,
        message = ex.message
    )

    constructor(errorCode: ErrorCode) : this(
        status = errorCode.status,
        message = errorCode.message
    )

}