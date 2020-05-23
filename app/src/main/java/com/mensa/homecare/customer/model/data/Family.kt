package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class Family(
    val address: String?,
    val created_at: String,
    val id: Int,
    val name: String,
    val nik: String?,
    val patient_id: Int,
    val phone_number: String?,
    val relation: String,
    val updated_at: String?
):Serializable