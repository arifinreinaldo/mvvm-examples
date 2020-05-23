package com.mensa.homecare.customer.ui.otp

import com.mensa.homecare.customer.model.repository.Repository
import com.mensa.homecare.customer.model.response.SimpleResponse
import com.mensa.homecare.customer.model.response.UserResponse
import com.mensa.homecare.customer.util.getMoshi
import com.mensa.homecare.customer.util.write


class OTPPresenter(val listener: OTPView) {
    fun requestCSRF(tag: String) {
        Repository.getCSRF({ response ->
            response?.let {
                val jsonAdapter =
                    getMoshi().adapter<SimpleResponse>(SimpleResponse::class.java)
                val data = jsonAdapter.fromJson(it)
                write(it)
                data?.let {
                    if (it.isSuccess()) {
                        listener.onSuccessCSRF(data.Message)
                    } else {
                        listener.onError(data.Message, tag)
                    }
                }
            }
        }, { anError ->
            anError?.let {
                listener.onError(it, tag)
            }
        })
    }

    fun requestOTP(csrf: String, phone: String, tag: String) {
        Repository.getOTP(csrf, phone, { response ->
            response?.let {
                write(it)
                val jsonAdapter =
                    getMoshi().adapter<SimpleResponse>(SimpleResponse::class.java)
                val data = jsonAdapter.fromJson(it)
                data?.let {
                    if (it.isSuccess()) {

                        listener.onSuccessReqOTP()
                    } else {
                        listener.onError(data.Message, tag)
                    }
                }
            }
        }, { anError ->
            anError?.let {
                listener.onError(it, tag)
            }
        })
    }

    fun submitOTP(otp: String, phone: String, token: String, tag: String) {
        Repository.postOTP(phone, otp, token, { response ->
            response?.let {
                write(it)
                val jsonAdapter =
                    getMoshi().adapter<UserResponse>(UserResponse::class.java)
                val data = jsonAdapter.fromJson(it)
                data?.let {
                    if (it.isSuccess()) {
                        listener.onSuccessSubmitOTP(it)
                    } else {
                        listener.onError(it.Message, tag)
                    }
                }
            }
        }, { anError ->
            anError?.let {
                listener.onError(it, tag)
            }
        })
    }
}