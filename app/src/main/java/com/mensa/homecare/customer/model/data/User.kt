package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class User(
    val address: String?,
    val birth_date: String?,
    val category_id: String?,
    val created_at: String?,
    val device_id: String?,
    val emr_id: String?,
    val family: List<Family>? = null,
    val fcm_token: String?,
    val gender: String?,
    var hospital_id: String? = "",
    val id: Int?,
    val image_url: String? = "",
    val is_active: String?,
    val name: String?,
    val nik: String? = "0",
    val otp_code: String?,
    val otp_status: String?,
    val phone_numbers: String?,
    val role_id: Int?,
    val role_name: String?,
    val status: String?,
    val updated_at: String?,
    val token: String?,
    val user_hospital: List<UserHospital>? = null
):Serializable