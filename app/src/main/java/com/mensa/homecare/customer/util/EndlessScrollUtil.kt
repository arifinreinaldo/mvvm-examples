package com.mensa.homecare.customer.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollUtil : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        val firstVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (mLoading && totalItemCount > mPreviousTotal) {
            mLoading = false
            mPreviousTotal = totalItemCount
        }
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            onLoadMore()
            mLoading = true
        }
    }

    fun resetScroll() {
        mPreviousTotal = 0
        mLoading = true
    }

    abstract fun onLoadMore()
}