package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class UserHospital(
    val created_at: String?,
    val hospital_id: Int?,
    val id: Int?,
    val is_active: String?,
    val price: Int?,
    val updated_at: String?,
    val user_id: Int
) : Serializable