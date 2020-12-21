package com.bnikolov.java2daysdemo.enum

enum class ErrorCode(val code: Int) {
    UNAUTHORIZED(401),
    CONFLICT(409),
    ERROR_CODE_GENERAL(500),
    ERROR_CODE_NO_INTERNET_CONNECTION(999);

    companion object {
        fun fromCode(code: Int): ErrorCode {
            for (error in values()) {
                if (error.code == code) return error
            }

            return ERROR_CODE_GENERAL
        }
    }
}