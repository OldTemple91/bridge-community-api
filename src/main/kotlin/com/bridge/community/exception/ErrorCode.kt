package com.bridge.community.exception

enum class ErrorCode(val status: Int, val message: String) {

    RESOURCE_NOT_FOUND(404, "Resource Not Found"),

    MEMBER_ALREADY_EXIST(400, "이미 가입한 멤버입니다."),
    INVALID_PHONE_NUM(400, "올바른 전화번호 형식이 아니에요"),
    INVALID_NAME(400, "이름은 한글로 띄어쓰기 없이 최대 8자까지 가능해요"),
    INVALID_PASSWORD(400, "비밀번호는 영문과 숫자 및 특수문자를 포함한 8~20자로 설정해주세요"),

    PASSWORD_ENCRYPTION_ERROR(401, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(401,"비밀번호 복호화에 실패하였습니다."),
    FAILED_TO_LOGIN(401, "로그인에 실패하였습니다."),
    EMPTY_JWT(401, "JWT를 입력해주세요"),
    INVALID_JWT(401,"유효하지 않은 JWT입니다"),
    INVALID_USER_JWT(401, "권한이 없는 유저의 접근입니다"),
    INVALID_AUTH_CODE(401, "잘못된 인증번호입니다"),

    TOPIC_NOT_FOUND(404, "해당 토픽이 없습니다."),
    MEMBER_NOT_FOUND(404, "탈퇴했거나 존재하지 않는 멤버입니다."),
    BOARD_NOT_FOUND(404, "해당 게시물이 없습니다."),
    REPLY_NOT_FOUND(404, "해당 댓글이 없습니다."),
    REPLY_NOT_MODIFY(401, "해당 댓글을 수정할 수 없습니다."),
    MEMBER_NOT_PERMISSION(401, "해당 토픽에 참여하지 않았습니다."),
    BOARD_NOT_MODIFY(401, "해당 게시물을 수정할 수 없습니다."),
    MEMBER_TOPIC_DUPLICATE(401, "이미 참여한 토픽입니다."),
    BOARD_RECOMMEND_DUPLICATE(401, "이미 추천한 게시글입니다."),
    RECOMMEND_NOT_FOUND(404, "게시글을 추천하지 않았습니다."),
    FEATURE_NOT_FOUND(404, "피쳐를 찾을 수 없습니다."),
    FEATURE_RECOMMEND_NOT_PERMISSION(401, "비회원은 참여할 수 없습니다."),

    INTERNAL_SERVER_ERROR(500, "internal server error"),
}