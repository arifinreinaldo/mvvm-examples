package com.mensa.homecare.customer.ui.fragment.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.data.Doctor
import com.mensa.homecare.customer.model.repository.Repository
import com.mensa.homecare.customer.model.response.DoctorResponse
import com.mensa.homecare.customer.util.getMoshi

class DoctorVM : ViewModel() {
    var initial = true
    var pDate: String = ""
    var pTime: String = ""
    var page: String = "1"
    var isLoading = MutableLiveData<Boolean>()
    var position = 0
    var cache: MutableList<Doctor> = mutableListOf()

    fun onLoadInitial(): LiveData<Boolean> {
        var rtn = MutableLiveData<Boolean>()
        if (initial) {
            initial = false
            rtn.value = true
        } else {
            rtn.value = false
        }
        return rtn
    }

    fun onLoadData(): LiveData<List<Doctor>> {
        var rtn = MutableLiveData<List<Doctor>>()

        if (isLoading.value == false || isLoading.value == null) {
            page = "1"
            cache.clear()

            isLoading.value = true
            Repository.getDoctorList(
                LocalValue.userData.hospital_id.toString(),
                "2",
                pDate,
                pTime,
                page,
                { success ->
                    isLoading.value = false
                    val jsonAdapter =
                        getMoshi().adapter<DoctorResponse>(DoctorResponse::class.java)
                    val data = jsonAdapter.fromJson(success)
                    data?.let { result ->
                        page = ""
                        result.Data?.nextPage?.let {
                            page = it
                        }
                        if (result.Data?.data == null) {
                            rtn.value = mutableListOf()
                        } else {
                            cache.addAll(result.Data.data)
                            rtn.value = result.Data.data
                        }
                    }
                },
                { error ->
                    isLoading.value = false
                })
        }
        return rtn
    }

    fun onNextData(): LiveData<List<Doctor>> {
        var rtn = MutableLiveData<List<Doctor>>()
        if (isLoading.value == false && page.isNotEmpty()) {
            isLoading.value = true
            Repository.getDoctorList(
                LocalValue.userData.hospital_id.toString(),
                "2",
                pDate,
                pTime,
                page,
                { success ->
                    isLoading.value = false
                    val jsonAdapter =
                        getMoshi().adapter<DoctorResponse>(DoctorResponse::class.java)
                    val data = jsonAdapter.fromJson(success)
                    data?.let { result ->
                        page = ""
                        result.Data?.nextPage?.let {
                            page = it
                        }
                        result.Data?.data?.let {
                            cache.addAll(it)
                            rtn.value = it
                        }
                    }
                },
                { error ->
                    isLoading.value = false
                })
        }
        return rtn
    }
}