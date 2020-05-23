package com.mensa.homecare.customer.model.response

import com.mensa.homecare.customer.base.BaseResponse
import com.mensa.homecare.customer.model.data.Doctor

data class DoctorResponse(
    val Data: DoctorData?,
    val Message: String?,
    val Status: Int
) : BaseResponse(Status)

data class DoctorData(
    val curPage: Int?,
    val `data`: List<Doctor>?,
    val nextPage: String?,
    val totalPage: Int?,
    val totalRec: Int
)