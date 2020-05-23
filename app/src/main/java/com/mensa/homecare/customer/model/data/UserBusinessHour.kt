package com.mensa.homecare.customer.model.data


data class UserBusinessHour(
    val created_at: String?,
    val day_of_week: Int?,
    val end_time: String?,
    val hospital_id: Int?,
    val id: Int?,
    val start_time: String?,
    val status: String?,
    val updated_at: String?,
    val user_id: Int
)