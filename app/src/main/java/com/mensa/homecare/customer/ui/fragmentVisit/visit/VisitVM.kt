package com.mensa.homecare.customer.ui.fragmentVisit.visit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.repository.Repository
import com.mensa.homecare.customer.model.response.Visit
import com.mensa.homecare.customer.model.response.VisitResponse
import com.mensa.homecare.customer.util.getMoshi

class VisitVM : ViewModel() {
    private var initial = true
    private var page: String = "1"
    var position = 0
    var isLoading = MutableLiveData<Boolean>()
    var status = MutableLiveData<String>()
    var data = MutableLiveData<VisitResponse>()
    var visits: MutableList<Visit> = mutableListOf()

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

    fun onLoadData(status: String): LiveData<List<Visit>> {
        var rtn = MutableLiveData<List<Visit>>()
        var isLoadingData = isLoading.value
        if (isLoading.value == false || isLoading.value == null) {
            this.status.value = status
            page = "1"
            visits.clear()

            isLoading.value = true
            Repository.getPatientHistory(
                LocalValue.userData.id.toString(),
                page,
                status,
                { success ->
                    isLoading.value = false
                    val jsonAdapter =
                        getMoshi().adapter<VisitResponse>(VisitResponse::class.java)
                    val result = jsonAdapter.fromJson(success)
                    result?.let { result ->
                        page = ""
                        result.Data.nextPage?.let {
                            page = it
                        }
                        if (result.Data.data == null) {
                            rtn.value = mutableListOf()
                        } else {
                            visits.addAll(result.Data.data)
                            rtn.value = result.Data.data
                        }
                    }
                },
                { error ->
                    isLoading.value = false
                }
            )
        }
        return rtn
    }

    fun onNextData(): LiveData<List<Visit>> {
        var rtn = MutableLiveData<List<Visit>>()
        if (isLoading.value == false && page.isNotEmpty()) {
            isLoading.value = true
            Repository.getPatientHistory(
                LocalValue.userData.id.toString(),
                page,
                status.value.toString(),
                { success ->
                    isLoading.value = false
                    val jsonAdapter =
                        getMoshi().adapter<VisitResponse>(VisitResponse::class.java)
                    val result = jsonAdapter.fromJson(success)
                    result?.let { result ->
                        page = ""
                        result.Data.nextPage?.let {
                            page = it
                        }
                        result.Data.data?.let {
                            visits.addAll(it)
                            rtn.value = it
                        }
                    }
                },
                { error ->
                    isLoading.value = false
                }
            )
        }
        return rtn
    }
}