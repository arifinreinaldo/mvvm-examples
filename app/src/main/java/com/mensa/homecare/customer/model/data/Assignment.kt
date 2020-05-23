package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class Assignment(
    val created_at: String?,
    val id: Int,
    val is_pic: String?,
    val is_used: String?,
    val price: Int,
    val reason: String?,
    val reschedule_date: String?,
    val role_id: Int,
    val schedule_id: Int,
    val status: String?,
    val status_name: String?,
    val updated_at: String?,
    val user: User?,
    val user_id: Int
) : Serializable