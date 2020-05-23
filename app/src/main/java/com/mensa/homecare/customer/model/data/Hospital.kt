package com.mensa.homecare.customer.model.data

import java.io.Serializable


data class Hospital(
    val address: String,
    val bank_account: String,
    val bank_name: String,
    val created_at: String,
    val id: Int,
    val is_active: String,
    val name: String,
    val phone_number: String,
    val price: Int,
    val updated_at: String
) : Serializable
