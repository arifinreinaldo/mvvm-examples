package com.mensa.homecare.customer.model.response

import com.mensa.homecare.customer.base.BaseResponse

data class SimpleResponse(
    val Message: String,
    val Status: Int
) : BaseResponse(Status)
