package com.mensa.homecare.customer.ui.fragment.request

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.util.*
import kotlinx.android.synthetic.main.fragment_request.*

/**
 * A simple [Fragment] subclass.
 */
class RequestFragment : BaseFragment() {
    private lateinit var dateFragment: DatePickerFragment
    private lateinit var timeFragment: TimePickerFragment
    private lateinit var vm: RequestVM
    override fun setLayoutId(): Int {
        return R.layout.fragment_request
    }

    override fun setInitialAsset() {
        vm = ViewModelProvider(this).get(RequestVM::class.java)
        val args: RequestFragmentArgs by navArgs()
        dateFragment = DatePickerFragment(act)
        timeFragment = TimePickerFragment(act)

        vm.pDate = args.pDate
        vm.pTime = args.pTime
        vm.doctor = args.doctorDetail

        tvVisitDate.setText(vm.pDate.displayDate())
        tvVisitTime.setText(vm.pTime.displayTime())

        ivDoctor.loadProfile(
            vm.doctor.image_url.toString(),
            getDrawable(R.drawable.ic_doc_placeholder, ctx)
        )
        tvDoctorName.text = vm.doctor.name
        tvDoctorSpesialis.text = vm.doctor.category?.name.toString()
        vm.doctor.user_hospital.firstOrNull()?.let {
            tvPaymentTotalValue.text = formatCurrency(it.price ?: 0)
        }
        etFullName.setText(LocalValue.userData.name)
        LocalValue.userData.birth_date?.let {
            if (it.isNotEmpty()) {
                etBirthdate.setText(it.displayDate())
            }
        }
        tvAddressValue.setText(LocalValue.userData.address)
        btRequest.setOnClickListener {
            vm.submitRequest().observe(this, Observer {
                when {
                    it.isSuccess() -> {
                        showMessage(it.Message)
                        startFragment(RequestFragmentDirections.actionRequestFragmentToHomeFragment())
                    }
                    it.isExpired() -> {

                    }
                    else -> {
                        showMessage(it.Message)
                    }
                }
            })
        }
        vm.isLoading.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun setListener() {
        tvMedicalPersonelChange.setOnClickListener {
            previousFragment()
        }
    }

    override fun removeListener() {

    }

    private fun showLoading() {
        slLoading.startShimmer()
        slLoading.showView()
        cvRequest.hideView()
        svRequest.hideView()
    }

    private fun hideLoading() {
        slLoading.stopShimmer()
        slLoading.hideView()
        cvRequest.showView()
        svRequest.showView()
    }
}
