package com.mensa.homecare.customer.base

interface BaseView {
    fun onError(value: Any, tag: String) {

    }

    fun setLoading(value: Boolean) {

    }

    fun getLoading(): Boolean {
        return false
    }

    fun onTokenExpired()
}
