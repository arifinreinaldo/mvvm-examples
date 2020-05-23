package com.mensa.homecare.customer.util

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}
