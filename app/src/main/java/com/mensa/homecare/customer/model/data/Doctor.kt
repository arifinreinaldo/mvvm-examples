package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class Doctor(
    val address: String?,
    val birth_date: String?,
    val category: Category?,
    val category_id: Int?,
    val created_at: String?,
    val device_id: String?,
    val emr_id: Int?,
    val family: List<String?>,
    val fcm_token: String?,
    val gender: String?,
    val id: Int?,
    val image_url: String?,
    val is_active: String?,
    val name: String?,
    val nik: String?,
    val otp_code: String?,
    val otp_status: String?,
    val phone_numbers: String?,
    val role_id: Int?,
    val role_name: String?,
    val updated_at: String?,
    val user_business_hour: List<UserBusinessHour>,
    val user_hospital: List<UserHospital>
) : Serializable


