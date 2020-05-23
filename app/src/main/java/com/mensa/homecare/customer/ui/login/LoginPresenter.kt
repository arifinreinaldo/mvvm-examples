package com.mensa.homecare.customer.ui.login

class LoginPresenter(val listener: LoginView) {
    fun validateNumber(phone: String) {
        var phone = phone
        if (phone.startsWith("+62")) {
            phone = phone.replaceFirst("+62", "")
        } else if (phone.startsWith("08")) {
            phone = phone.replaceFirst("08", "8")
        }
        if (phone.length >= 9) {
            listener.toOTP(phone)
        }
        listener.hideKeyboard()
    }
}