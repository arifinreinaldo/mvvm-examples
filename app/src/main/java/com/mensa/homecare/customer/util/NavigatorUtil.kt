package com.mensa.homecare.customer.util

import android.app.Activity
import android.content.Intent
import com.mensa.homecare.customer.local.Constants.PHONE_NUMBER
import com.mensa.homecare.customer.ui.login.LoginActivity
import com.mensa.homecare.customer.ui.main.MainActivity
import com.mensa.homecare.customer.ui.otp.OTPActivity

class NavigatorUtil(val activity: Activity) {
    fun toLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun toMain() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
        activity.finish()
    }

    //
    fun toOTP(value: String) {
        val intent = Intent(activity, OTPActivity::class.java)
        intent.putExtra(PHONE_NUMBER, value)
        activity.startActivity(intent)
    }
//
}