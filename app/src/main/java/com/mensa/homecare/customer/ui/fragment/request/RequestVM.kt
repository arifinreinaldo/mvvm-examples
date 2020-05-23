package com.mensa.homecare.customer.ui.fragment.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.data.Doctor
import com.mensa.homecare.customer.model.repository.Repository
import com.mensa.homecare.customer.model.response.SimpleResponse
import com.mensa.homecare.customer.util.getMoshi

class RequestVM : ViewModel() {
    var pDate: String = ""
    var pTime: String = ""
    var isLoading = MutableLiveData<Boolean>()
    lateinit var doctor: Doctor

    fun submitRequest(): LiveData<SimpleResponse> {
        isLoading.value = true
        var rtn = MutableLiveData<SimpleResponse>()
        Repository.postCreateSchedule(
            LocalValue.userData.hospital_id.toString(),
            LocalValue.userData.id.toString(),
            "$pDate $pTime",
            "",
            "",
            doctor.id.toString(),
            { success ->
                isLoading.value = false
                val jsonAdapter =
                    getMoshi().adapter<SimpleResponse>(SimpleResponse::class.java)
                val data = jsonAdapter.fromJson(success)
                data?.let { result ->
                    rtn.value = result
                }
            },
            { error ->
                isLoading.value = false

            })
        return rtn
    }
}