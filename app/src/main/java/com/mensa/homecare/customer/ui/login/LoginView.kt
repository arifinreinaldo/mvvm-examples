package com.mensa.homecare.customer.ui.login

interface LoginView {
    fun hideKeyboard()
    fun toOTP(phone: String)
}