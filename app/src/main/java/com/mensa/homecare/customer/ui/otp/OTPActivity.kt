package com.mensa.homecare.customer.ui.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import com.androidnetworking.error.ANError
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.tasks.Task
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseActivity
import com.mensa.homecare.customer.local.Constants.OTP_EXTRA
import com.mensa.homecare.customer.local.Constants.OTP_INTENT
import com.mensa.homecare.customer.local.Constants.PHONE_NUMBER
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.response.UserResponse
import com.mensa.homecare.customer.util.*
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.view_otp.*
import java.util.concurrent.TimeUnit

const val CSRF = "csrf"
const val REQOTP = "Request OTP"
const val SUBOTP = "Submit OTP"

const val SECOND_IN_MINUTES = 60

class OTPActivity : BaseActivity(), OTPView {
    private lateinit var presenter: OTPPresenter
    private lateinit var client: SmsRetrieverClient
    private lateinit var task: Task<Void>
    private lateinit var broadCastReceiver: BroadcastReceiver
    private var ctDown: CountDownTimer? = null
    private var cdOTP: CountDownTimer? = null
    private var currentCSRF = ""
    private lateinit var phone: String

    override fun setLayoutId(): Int {
        return R.layout.activity_otp
    }

    override fun setInitial() {
        presenter = OTPPresenter(this)
        client = SmsRetriever.getClient(ctx)
        presenter = OTPPresenter(this)

        tvCountdown.text = getString(R.string.msg_otp_wait_confirm)
        btSubmit.disableFunction(ctx)
        btSubmit.setOnClickListener {
            submitOTP(otp_view.validateOTP())
        }
        otp_view.listener = object : OTPLibrary.OTPListener {
            override fun onError() {
                btSubmit.disableFunction(ctx)
            }

            override fun onSuccess(otp: String) {
                btSubmit.enableFunction(ctx)
                submitOTP(otp)
            }
        }

        intent.getStringExtra(PHONE_NUMBER)?.let {
            phone = it
        }
        if (phone.isNotEmpty()) {
            tvMessage.text = getString(R.string.msg_otp_info, phone)
            presenter.requestCSRF(CSRF)
        } else {
            showMessage(R.string.err_reinput_number)
            finish()
        }
        tvWrong?.setOnClickListener {
            doConfirmChangeNumber()
        }
        tvCountdown?.setOnClickListener {
            if (tvCountdown.text.equals(getString(R.string.msg_otp_resend))) {
                tvCountdown.text = getString(R.string.msg_otp_wait_confirm)
                presenter.requestOTP(currentCSRF, phone, REQOTP)
            }
        }

        broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    var otp = it.getStringExtra(OTP_EXTRA)
                    otp?.let { str ->
                        if (str.isNotEmpty()) {
                            val otpSTR = str.replace("#Halodoc Midwives otp: ", "").take(6)
                            var idx = 1
                            tvCountdown.hideView()
                            cdOTP = simpleCountDown(3.5, 0.5, {

                            }, {
                                when (idx) {
                                    1 -> {
                                        etOTP1.setText(otpSTR[0].toString())
                                    }
                                    2 -> {
                                        etOTP2.setText(otpSTR[1].toString())
                                    }
                                    3 -> {
                                        etOTP3.setText(otpSTR[2].toString())
                                    }
                                    4 -> {
                                        etOTP4.setText(otpSTR[3].toString())
                                    }
                                    5 -> {
                                        etOTP5.setText(otpSTR[4].toString())
                                    }
                                    6 -> {
                                        etOTP6.setText(otpSTR[5].toString())
                                    }
                                }
                                idx++
                            }
                            ).start()
                        }
                    }
                }
            }
        }
        registerReceiver(broadCastReceiver, IntentFilter(OTP_INTENT))
    }

    override fun onSuccessCSRF(value: String) {
        this.currentCSRF = value
        presenter.requestOTP(currentCSRF, phone, REQOTP)
    }


    override fun onSuccessReqOTP() {
        val current_phone = LocalValue.current_number
        LocalValue.current_retry = if (current_phone != phone) {
            1
        } else {
            LocalValue.current_retry + 1
        }
        LocalValue.current_number = phone
        countDown()
        SMSListener()
    }

    fun submitOTP(otp: String) {
        presenter.submitOTP(otp, phone, token = LocalValue.fcm, tag = SUBOTP)
    }

    override fun onSuccessSubmitOTP(user: UserResponse) {
        user.Data?.let {
            LocalValue.current_retry = 1
            LocalValue.is_logged = true
            var hospital_id = ""
            user.Data?.user_hospital?.forEach {
                hospital_id += "," + it.hospital_id
            }
            user.Data.hospital_id = hospital_id.substring(1)
            LocalValue.userData = user.Data
            nav.toMain()
        }
    }

    private fun doConfirmChangeNumber() {
        val dialog = confirmDialog(
            ctx = ctx,
            title = getString(R.string.msg_otp_change_number),
            positive = getString(R.string.bt_yes),
            negative = getString(R.string.bt_no),
            posFun = { finish() })
        dialog.show()
    }

    override fun onError(value: Any, tag: String) {
        if (value is ANError) {
            val error = value to ANError()
        } else {
            when (tag) {
                CSRF -> {
                    showMessage(value.toString())
                    finish()
                }
                REQOTP -> {
                    showMessage(value.toString())
                    finish()
                }
                SUBOTP -> {
                    showMessage(value.toString())
                }
                else -> {
                    showMessage(getString(R.string.err_info))
                }
            }
        }
    }

    private fun countDown() {
        val retry = LocalValue.current_retry
        var count = if (retry < 4) {
            SECOND_IN_MINUTES
        } else {
            SECOND_IN_MINUTES * 5
        }
        ctDown = simpleCountDown(count.toDouble(), 1.0,
            finish = {
                tvCountdown.setText(getString(R.string.msg_otp_resend))
            }, tick = {
                count--
                val minute = TimeUnit.SECONDS.toMinutes(count.toLong())
                val second =
                    TimeUnit.SECONDS.toSeconds((count - minute * SECOND_IN_MINUTES))
                tvCountdown.setText(getString(R.string.msg_resubmit, minute, second))
            })
        ctDown?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        ctDown?.let {
            it.cancel()
        }
        cdOTP?.let {
            it.cancel()
        }
    }

    private fun SMSListener() {
        val smsClient = SmsRetriever.getClient(ctx)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener { aVoid ->
        }
        task.addOnFailureListener { exception ->
            showMessage("FAIL SMS" + exception.toString())
        }
    }

    override fun onSuccessSMS(value: String) {
        showMessage("SMS $value")
    }

    override fun onErrorSMS(value: String) {
    }

    override fun onStop() {
        super.onStop()
        try {
            if (broadCastReceiver != null) {
                unregisterReceiver(broadCastReceiver)
            }
        } catch (e: Exception) {

        }
    }
}
