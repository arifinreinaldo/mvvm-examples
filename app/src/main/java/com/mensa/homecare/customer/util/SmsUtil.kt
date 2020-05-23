package com.mensa.homecare.customer.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.ui.otp.OTPView


class SmsUtil : BroadcastReceiver() {
    private lateinit var listener: OTPView

    fun setListener(otp: OTPView) {
        listener = otp
    }

    override fun onReceive(context: Context?, data: Intent?) {
        data?.let { intent ->
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.getAction()) {
                intent.extras?.let { extra ->
                    val status = extra.get(SmsRetriever.EXTRA_STATUS) as Status?
                    status?.let { stat ->
                        when (stat.statusCode) {
                            CommonStatusCodes.SUCCESS -> {
                                val msg = extra.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                                write(msg)
                                val send = Intent(Constants.OTP_INTENT)
                                val extras = Bundle()
                                extras.putString(Constants.OTP_EXTRA, msg)
                                send.putExtras(extras)
                                context?.sendBroadcast(send)
                            }
                            CommonStatusCodes.TIMEOUT -> {

                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }

    }

}