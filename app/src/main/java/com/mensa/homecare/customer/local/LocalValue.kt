package com.mensa.homecare.customer.local

import android.content.Context
import android.content.SharedPreferences
import com.mensa.homecare.customer.model.data.User

object LocalValue {
    private lateinit var preferences: SharedPreferences
    private const val MODE = Context.MODE_PRIVATE
    private const val NAME = "HOMECARE_CASTAMER_"

    val CURRENT_NUMBER = "${NAME}current_phone_number"
    val CURRENT_NUMBER_RETRY = "${NAME}current_phone_number_retry"
    val ISLOGGED = "${NAME}is_logged"
    val BIRTHDATE = "${NAME}birth_date"
    val CRTDATE = "${NAME}created_at"
    val DEVICEID = "${NAME}device_id"
    val FCMTOK = "${NAME}fcm_token"
    val GENDER = "${NAME}gender"
    val HOSPID = "${NAME}hostpital"
    val USERID = "${NAME}id"
    val USERNAM = "${NAME}name"
    val OTPCOD = "${NAME}otp_code"
    val OTPSTS = "${NAME}otp_status"
    val PHONUM = "${NAME}phone_numbers"
    val ROLID = "${NAME}role_id"
    val STATS = "${NAME}status"
    val TOKEN = "${NAME}token"
    val UPDAT = "${NAME}updated_at"
    val EMRID = "${NAME}emr_id"
    val BDGCNT = "${NAME}badge_count"

    fun init(ctx: Context) {
        preferences = ctx.getSharedPreferences(NAME, MODE)
    }

    var current_number: String
        get() = preferences.getString(CURRENT_NUMBER, "").toString()
        set(value) = setPref(value, CURRENT_NUMBER)

    var current_retry: Int
        get() = preferences.getInt(CURRENT_NUMBER_RETRY, 0)
        set(value) = setPref(value, CURRENT_NUMBER_RETRY)
    val isDocter: Boolean
        get() {
            return preferences.getInt(ROLID, 0) == Constants.DOCTOR
        }
    var is_logged: Boolean
        get() = preferences.getBoolean(ISLOGGED, false)
        set(value) = setPref(value, ISLOGGED)
    val token: String
        get() = preferences.getString(TOKEN, "").toString()
    val emr_id: String
        get() = preferences.getString(EMRID, "").toString()
    var fcm: String
        get() = preferences.getString(FCMTOK, "").toString()
        set(value) = setPref(value, FCMTOK)
    var badgeCount: Int
        get() = preferences.getInt(BDGCNT, 0)
        set(value) = setPref(value, BDGCNT)
    var userData: User
        get() {
            return User(
                birth_date = preferences.getString(BIRTHDATE, ""),
                created_at = preferences.getString(CRTDATE, ""),
                device_id = preferences.getString(DEVICEID, ""),
                fcm_token = preferences.getString(FCMTOK, ""),
                gender = preferences.getString(GENDER, ""),
                id = preferences.getInt(USERID, -1),
                name = preferences.getString(USERNAM, ""),
                otp_code = preferences.getString(OTPCOD, ""),
                otp_status = preferences.getString(OTPSTS, ""),
                phone_numbers = preferences.getString(PHONUM, ""),
                role_id = preferences.getInt(ROLID, 0),
                status = preferences.getString(STATS, ""),
                token = preferences.getString(TOKEN, ""),
                updated_at = preferences.getString(UPDAT, ""),
                address = null,
                role_name = null,
                emr_id = preferences.getString(EMRID, ""),
                is_active = null,
                family = emptyList(),
                hospital_id = preferences.getString(HOSPID, ""),
                category_id = "0"
            )
        }
        set(value) {
            setPref(value.birth_date, BIRTHDATE)
            setPref(value.created_at, CRTDATE)
            setPref(value.device_id, DEVICEID)
            setPref(value.fcm_token, FCMTOK)
            setPref(value.gender, GENDER)
            setPref(value.id, USERID)
            setPref(value.name, USERNAM)
            setPref(value.otp_code, OTPCOD)
            setPref(value.otp_status, OTPSTS)
            setPref(value.phone_numbers, PHONUM)
            setPref(value.role_id, ROLID)
            setPref(value.status, STATS)
            setPref(value.token, TOKEN)
            setPref(value.updated_at, UPDAT)
            setPref(value.emr_id, EMRID)
            setPref(value.hospital_id, HOSPID)
        }


    fun setPref(value: Any?, TAG: String) {
        value?.let {
            val editor = preferences.edit()
            if (value is String) {
                editor.putString(TAG, value)
            } else if (value is Int) {
                editor.putInt(TAG, value)
            } else if (value is Boolean) {
                editor.putBoolean(TAG, value)
            }
            editor.apply()
        }
    }
}