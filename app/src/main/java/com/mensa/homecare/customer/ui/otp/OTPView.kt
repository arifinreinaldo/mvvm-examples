package com.mensa.homecare.customer.ui.otp

import com.mensa.homecare.customer.model.response.UserResponse

interface OTPView {
    fun onSuccessCSRF(value: String)
    fun onSuccessReqOTP()
    fun onSuccessSubmitOTP(user: UserResponse)
    fun onSuccessSMS(value: String)
    fun onErrorSMS(value: String)
    fun onError(value: Any, tag: String) {

    }
}