package com.mensa.homecare.customer.base


open class BaseResponse(
    val status: Int?
) {

    fun isSuccess(): Boolean {
        status?.let {
            return it == 1
        }
        return false
    }

    fun isExpired(): Boolean {
        status?.let {
            return it == 401
        }
        return false
    }
}