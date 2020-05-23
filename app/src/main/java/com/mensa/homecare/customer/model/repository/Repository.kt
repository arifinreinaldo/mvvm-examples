package com.mensa.homecare.customer.model.repository

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.util.getHeader
import com.mensa.homecare.customer.util.getPath
import com.mensa.homecare.customer.util.parseANError
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

object Repository {
    fun init(ctx: Context) {
        val okHTTP =
            OkHttpClient().newBuilder().connectTimeout(Constants.time_out, TimeUnit.SECONDS)
                .writeTimeout(Constants.time_out, TimeUnit.SECONDS)
                .readTimeout(Constants.time_out, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        var response = chain.proceed(request)
                        var tryCount = 0
                        while (!response.isSuccessful && tryCount < Constants.retry) {
                            tryCount++
                            response = chain.proceed(request)
                        }
                        return response
                    }
                })
                .addNetworkInterceptor(StethoInterceptor()).build()
        AndroidNetworking.initialize(ctx, okHTTP)
    }


    //Login
    fun getCSRF(success: (String?) -> Unit, error: (String?) -> Unit) {
        AndroidNetworking.get(getPath("hl-csrf"))
            .addHeaders(getHeader())
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    fun getOTP(csrf: String, phone: String, success: (String?) -> Unit, error: (String?) -> Unit) {
        AndroidNetworking.post(getPath("otp/send"))
            .addHeaders(getHeader())
            .addHeaders("X-CSRF-TOKEN", csrf)
            .addQueryParameter("phonenumber", phone)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    fun postOTP(
        phone: String,
        otp: String,
        token: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.post(getPath("otp/verify"))
            .addHeaders(getHeader())
            .addQueryParameter("phonenumber", phone)
            .addQueryParameter("otp", otp)
            .addQueryParameter("fcm_token", token)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    //Request Doctor Visit
    fun getDoctorList(
        hospital_id: String,
        role_ids: String,
        date: String,
        time: String,
        page: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        val run = AndroidNetworking.get(getPath("user")).addHeaders(getHeader())
            .addQueryParameter("hospital_id", hospital_id)
            .addQueryParameter("role_ids", role_ids)
            .addQueryParameter("date", date)
            .addQueryParameter("page", page)
//            .addQueryParameter("time", time)
            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    fun postCreateSchedule(
        hospital_id: String,
        patient_id: String,
        visit_time: String,
        notes: String,
        role_ids: String,
        doctor_id: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.post(getPath("schedule")).addHeaders(getHeader())
            .addBodyParameter("hospital_id", hospital_id)
            .addBodyParameter("patient_id", patient_id).addBodyParameter("visit_time", visit_time)
            .addBodyParameter("assignment_req", role_ids)
            .addBodyParameter("doctor_id", doctor_id)
            .addBodyParameter("note", notes).setPriority(Priority.HIGH).build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }
    //End of Request Doctor Visit

    //Visit History
    fun getPatientHistory(
        patient_id: String,
        page: String,
        status: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.cancel("getPatientHistory")
        AndroidNetworking.get(getPath("schedule")).addHeaders(getHeader())
            .addQueryParameter("status", status)
            .setTag("getPatientHistory")
            .addQueryParameter("p", page)
            .addQueryParameter("patient_id", patient_id).setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(anError.toString())
                }
            })
    }
    //End of Visit History

    //Menu
    fun getCount(success: (String?) -> Unit, error: (String?) -> Unit) {
        AndroidNetworking.get(getPath("home")).addHeaders(getHeader()).build().getAsString(
            object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }

            }
        )
    }

    //Approval / Schedule
    fun getSchedule(
        status: String,
        page: String,
        query: String,
        user: String = "",
        filterStart: String = "",
        filterEnd: String = "",
        filterStatus: String = "",
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        val run = AndroidNetworking.get(getPath("schedule")).addHeaders(getHeader())
            .addQueryParameter("p", page).addQueryParameter("q", query)
            .addQueryParameter("status", status)
            .addQueryParameter("createdby_id", user)
            .addQueryParameter("start_date", filterStart)
            .addQueryParameter("end_date", filterEnd)
            .addQueryParameter("approval", filterStatus)
            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    fun getDetail(
        id: String,
        tag: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.get(getPath("schedule/{id}")).addHeaders(getHeader())
            .addPathParameter("id", id)
            .setTag(tag)
            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    //Approval
    fun postApproval(
        id_assignment: String,
        status: String,
        reason: String,
        date: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.put(getPath("approval/{id}")).addHeaders(getHeader())
            .addPathParameter("id", id_assignment).addBodyParameter("status", status)
            .addBodyParameter("reason", reason)
            .addBodyParameter("reschedule_date", date).setPriority(Priority.HIGH).build()
            .getAsString(
                object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        error(parseANError(anError))
                    }
                }
            )
    }

    //Schedule
    fun getTimelineID(
        id: String,
        patient_id: String,
        medical_staff_id: String,
        tag: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.post(getPath("timeline")).addHeaders(getHeader()).setTag(tag)
            .addBodyParameter("schedule_id", id).addBodyParameter("emr_patient_id", patient_id)
            .addBodyParameter("emr_medical_staff_id", medical_staff_id)
            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }
            })
    }

    fun getTimelineData(
        timeline_id: String,
        tag: String,
        success: (String?) -> Unit,
        error: (String?) -> Unit
    ) {
        AndroidNetworking.get(getPath("timeline/{timeline_id}")).addHeaders(getHeader())
            .setTag(tag)
            .addPathParameter("timeline_id", timeline_id)
            .setPriority(Priority.MEDIUM).build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    success(response)
                }

                override fun onError(anError: ANError?) {
                    error(parseANError(anError))
                }

            })
    }
//
//    fun postBasicData(
//        id: String,
//        basic: TimelineData,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.put(getPath("timeline/{id}")).addHeaders(getHeader())
//            .addPathParameter("id", id)
//            .addBodyParameter("blood_pressure_a", basic.blood_pressure_a)
//            .addBodyParameter("blood_pressure_b", basic.blood_pressure_b)
//            .addBodyParameter("height", basic.height)
//            .addBodyParameter("weight", basic.weight)
//            .setPriority(Priority.MEDIUM).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//
//            })
//    }
//
//    fun getSOAPData(
//        timeline_id: String,
//        emr_id: String?,
//        tag: String,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.get(getPath("soap")).addHeaders(getHeader())
//            .addQueryParameter("timeline_id", timeline_id)
//            .setTag(tag)
//            .addQueryParameter("medical_staff_id", emr_id ?: LocalValue.emr_id)
//            .setPriority(Priority.MEDIUM).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//
//            })
//    }
//
//    fun postSOAPData(
//        data: ParameterSoap,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        val req =
//            AndroidNetworking.upload(getPath("soap")).addHeaders(getHeader())
//                .addMultipartParameter("timeline_id", data.timeline_id)
//                .addMultipartParameter("subject", data.subject)
//                .addMultipartParameter("object", data.`object`)
//                .addMultipartParameter("assessment", data.assessment)
//                .addMultipartParameter("planning", data.planning)
//                .addMultipartParameter("recommendation", data.recommendation)
//                .addMultipartParameter("diagnosis", data.action)
//                .addMultipartParameter("emr_medical_staff_id", LocalValue.emr_id)
//        data.subject_rec?.let {
//            if (it.exists()) {
//                req.addMultipartFile("subject_rec", it)
//            }
//        }
//        data.object_rec?.let {
//            if (it.exists()) {
//                req.addMultipartFile("object_rec", it)
//            }
//        }
//        data.assessment_rec?.let {
//            if (it.exists()) {
//                req.addMultipartFile("assessment_rec", it)
//            }
//        }
//        data.planning_rec?.let {
//            if (it.exists()) {
//                req.addMultipartFile("planning_rec", it)
//            }
//        }
//        data.files?.let {
//            req.addMultipartFileList("images[]", it)
//        }
//        req.setPriority(Priority.MEDIUM).build().getAsString(
//            object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//
//            })
//    }
//
//    fun getPaymentData(
//        id: String,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.get(getPath("schedule/{id}")).addHeaders(getHeader())
//            .addPathParameter("id", id)
//            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    fun postPaymentData(
//        id: String,
//        total: String,
//        type: String,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.post(getPath("payment")).addHeaders(getHeader())
//            .addBodyParameter("schedule_id", id).addBodyParameter("total", total)
//            .addBodyParameter("type", type)
//            .setPriority(Priority.MEDIUM).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//
//            })
//    }
//
//    //notifications
//    fun getNotificatioCount(success: (String?) -> Unit, error: (String?) -> Unit) {
//        AndroidNetworking.get(getPath("count-notif")).addHeaders(getHeader())
//            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    fun getNotification(page: String, success: (String?) -> Unit, error: (String?) -> Unit) {
//        AndroidNetworking.get(getPath("notification")).addHeaders(getHeader())
//            .addQueryParameter("p", page)
//            .setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    fun setReadNotification(notif: String, success: (String?) -> Unit, error: (String?) -> Unit) {
//        AndroidNetworking.get(getPath("notification/{id}")).addHeaders(getHeader())
//            .addPathParameter("id", notif).setPriority(Priority.HIGH).build()
//            .getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    // follow up
//    fun getPatientData(
//        page: String,
//        query: String,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.get(getPath("user")).addHeaders(getHeader()).addQueryParameter("p", page)
//            .addQueryParameter("q", query).setPriority(Priority.HIGH).build()
//            .getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    fun postCreateSchedule(
//        hospital_id: String,
//        patient_id: String,
//        visit_time: String,
//        notes: String,
//        role_ids: String,
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        AndroidNetworking.post(getPath("schedule")).addHeaders(getHeader())
//            .addBodyParameter("hospital_id", hospital_id)
//            .addBodyParameter("patient_id", patient_id).addBodyParameter("visit_time", visit_time)
//            .addBodyParameter("assignment_req", role_ids)
//            .addBodyParameter("note", notes).setPriority(Priority.HIGH).build()
//            .getAsString(object : StringRequestListener {
//                override fun onResponse(response: String?) {
//                    success(response)
//                }
//
//                override fun onError(anError: ANError?) {
//                    error(parseANError(anError))
//                }
//            })
//    }
//
//    fun putEditSchedule(
//        schedule_id: String,
//        hospital_id: String,
//        patient_id: String,
//        visit_time: String,
//        notes: String,
//        role_ids: String,
//        status: String = "",
//        success: (String?) -> Unit,
//        error: (String?) -> Unit
//    ) {
//        val request =
//            AndroidNetworking.put(getPath("schedule/{id}")).addPathParameter("id", schedule_id)
//                .addHeaders(
//                    getHeader()
//                )
//        when (status) {
//            Constants.REJECT -> {
//                request.addBodyParameter("status", status)
//                request.addBodyParameter("reason", notes)
//            }
//            else -> {
//                request.addBodyParameter("hospital_id", hospital_id)
//                    .addBodyParameter("patient_id", patient_id)
//                    .addBodyParameter("visit_time", visit_time)
//                    .addBodyParameter("notes", notes).addBodyParameter("assignment_req", role_ids)
//            }
//        }
//
//        request.setPriority(Priority.HIGH).build().getAsString(object : StringRequestListener {
//            override fun onResponse(response: String?) {
//                success(response)
//            }
//
//            override fun onError(anError: ANError?) {
//                error(parseANError(anError))
//            }
//        })
//    }
//
}