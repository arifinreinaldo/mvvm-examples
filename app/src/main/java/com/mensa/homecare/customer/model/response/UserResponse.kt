package com.mensa.homecare.customer.model.response

import com.mensa.homecare.customer.base.BaseResponse
import com.mensa.homecare.customer.model.data.User

data class UserResponse(
    val Data: User?,
    val Message: String,
    val Status: Int
) : BaseResponse(Status)

