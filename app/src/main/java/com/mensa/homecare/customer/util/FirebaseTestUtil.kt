package com.mensa.apotikb2b.util

import android.annotation.SuppressLint
import android.provider.Settings
import com.mensa.homecare.customer.BuildConfig
import com.mensa.homecare.customer.base.App
import com.mensa.homecare.customer.local.Constants

object FirebaseTestUtil {
    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        var deviceId =
            Settings.Secure.getString(App.context.contentResolver, Settings.Secure.ANDROID_ID)
        if (BuildConfig.DEBUG && Constants.IS_FIREBASE_TEST_LAB) {
            deviceId = Constants.TEST_DEVICE_ID
        }
        return deviceId
    }
}