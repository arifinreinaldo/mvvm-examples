package com.mensa.homecare.customer.util

import android.os.Build
import com.mensa.apotikb2b.util.FirebaseTestUtil
import com.mensa.homecare.customer.BuildConfig
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.local.LocalValue

fun getPath(value: String): String {
    return "${BuildConfig.URL_API}$value"
}

private val APP_KEY = Constants.APP_KEY

//String(Base64.decode(Constants.APP_KEY, Base64.DEFAULT))
private val TOKEN = LocalValue.token
private val DEVICE_ID = FirebaseTestUtil.getDeviceId()
private val OS_NAME = Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name

fun getHeader(): Map<String, String> {
    val headers = HashMap<String, String>()
    headers["token"] = LocalValue.token
    headers["appkey"] = APP_KEY
    headers["deviceid"] = DEVICE_ID
    headers["os"] = OS_NAME
    headers["Android-Version"] = BuildConfig.VERSION_NAME
    return headers
}