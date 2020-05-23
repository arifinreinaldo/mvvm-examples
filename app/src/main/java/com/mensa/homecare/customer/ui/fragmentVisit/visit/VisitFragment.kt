package com.mensa.homecare.customer.ui.fragmentVisit.visit

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.model.data.Status
import com.mensa.homecare.customer.model.response.Visit
import com.mensa.homecare.customer.model.response.VisitResponse
import com.mensa.homecare.customer.util.*
import kotlinx.android.synthetic.main.fragment_visit.*


/**
 * A simple [Fragment] subclass.
 */
class VisitFragment : BaseFragment() {
    private lateinit var adapterStatus: VisitStatusAdapter
    private lateinit var adapter: VisitAdapter
    private lateinit var vm: VisitVM
    private lateinit var scrollListen: EndlessScrollUtil
    private lateinit var dataObserver: Observer<VisitResponse>
    override fun setLayoutId(): Int {
        return R.layout.fragment_visit
    }

    override fun setInitialAsset() {
        vm = ViewModelProvider(this).get(VisitVM::class.java)
        tagNetworking = this::class.toString()
        adapterStatus = VisitStatusAdapter(ctx)
        adapter = VisitAdapter(ctx)
        rvStatus.init(ctx, horizontal = true)
        rvResult.init(ctx)
        scrollListen = scrollListener {
            onNextData()
        }
        onLoadInitial()
    }

    override fun setListener() {
        adapterStatus.listen = object : VisitStatusAdapter.ApprovalListener {
            override fun onItemClicked(item: Status, position: Int) {
                scrollListen.resetScroll()
                rvStatus.smoothScrollToPosition(position)
                onSearchData(item.status_data)
                adapterStatus.notifyDataSetChanged()
            }
        }
        rvStatus.adapter = adapterStatus
        adapterStatus.setItem(populateStatus())

        adapter.listen = object : VisitAdapter.ApprovalListener {
            override fun onItemClicked(item: Visit, position: Int) {
                vm.position = position
                startFragment(VisitFragmentDirections.actionVisitFragmentToDetailFragment(item))
            }
        }
        rvResult.adapter = adapter
        adapter.addAllItem(vm.visits)
        rvResult.addOnScrollListener(scrollListen)
        setObserver()
    }

    private fun setObserver() {
        vm.status.observe(this, Observer { curStatus ->
            adapterStatus.getItem().forEachIndexed() { idx, status ->
                status.selected = status.status_data.equals(curStatus)
            }
        })
        vm.isLoading.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun removeListener() {
        rvResult.removeOnScrollListener(scrollListen)
        vm.data.removeObservers(viewLifecycleOwner)
        rvResult.adapter = null
    }

    private fun showLoading() {
        rvStatus.isEnabled = false
        llResult.showView()
        slDoctor.startShimmer()
        slDoctor.showView()
        rvResult.hideView()
        llEmpty.hideView()
    }

    private fun hideLoading() {
        rvStatus.isEnabled = true
        llResult.showView()
        rvResult.showView()
        slDoctor.stopShimmer()
        slDoctor.hideView()
        llEmpty.hideView()
    }

    private fun showEmpty() {
        llEmpty.showView()
        llResult.hideView()
    }

    private fun populateStatus(): MutableList<Status> {
        val listStatus = mutableListOf<Status>()
        listStatus.add(Status(getString(R.string.msg_approval_all), ""))
        listStatus.add(Status(getString(R.string.msg_approval_inprogress), Constants.TERBENTUK))
        listStatus.add(Status(getString(R.string.msg_approval_finished), Constants.DONE))
        return listStatus
    }

    private fun onLoadInitial() {
        vm.onLoadInitial().observe(viewLifecycleOwner, Observer {
            if (it) {
                onSearchData("")
            } else {
                rvResult.scrollToPosition(vm.position)
            }
        })
    }

    private fun onSearchData(status: String) {
        vm.onLoadData(status).observe(viewLifecycleOwner, Observer {
            adapter.clear()
            if (it.isNotEmpty()) {
                adapter.setItem(it.toMutableList())
                rvResult.scrollToPosition(0)
            } else {
                showEmpty()
            }
        })
    }

    private fun onNextData() {
        vm.onNextData().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                adapter.addAllItem(it.toMutableList())
            }
        })
    }
}
