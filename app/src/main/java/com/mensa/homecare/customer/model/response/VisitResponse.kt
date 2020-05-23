package com.mensa.homecare.customer.model.response

import com.mensa.homecare.customer.base.BaseResponse
import com.mensa.homecare.customer.model.data.Assignment
import com.mensa.homecare.customer.model.data.Hospital
import com.mensa.homecare.customer.model.data.User
import java.io.Serializable

data class VisitResponse(
    val Data: VisitData,
    val Message: String,
    val Status: Int
) : BaseResponse(Status)

data class VisitData(
    val curPage: String?,
    val `data`: List<Visit>?,
    val nextPage: String? = "",
    val totalRec: Int? = 0
)

data class Visit(
    val assignment: List<Assignment>?,
    val assignment_req: String?,
    val created_at: String?,
//    val createdby_id: Int?,
    val createdby_name: String?,
    val diagnosis: String?,
    val dpjp_id: String?,
    val emr_timeline_id: String?,
    val hospital: Hospital,
    val hospital_id: Int,
    val id: Int,
    val is_reschedule: String?,
    val note: String?,
    val patient: User?,
    val patient_id: Int,
    val reason: String?,
    val status: String?,
    val status_name: String?,
    val symptom: String?,
    val total: Int,
    val updated_at: String?,
//    val updatedby_id: String?,
    val updatedby_name: String?,
    val user: User?,
    val visit_time: String?
) : Serializable