package com.mensa.homecare.customer.ui.fragment.doctor

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.model.data.Doctor
import com.mensa.homecare.customer.util.*
import kotlinx.android.synthetic.main.fragment_doctor.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorFragment : BaseFragment() {
    private lateinit var layout: View
    private lateinit var dialog: BottomSheetDialog
    private lateinit var adapter: DoctorAdapter
    private lateinit var vm: DoctorVM
    private lateinit var dateFragment: DatePickerFragment
    private lateinit var timeFragment: TimePickerFragment
    private lateinit var textWatcher: TextWatcher
    override fun setLayoutId(): Int {
        return R.layout.fragment_doctor
    }

    override fun setInitialAsset() {
        dateFragment = DatePickerFragment(act)
        timeFragment = TimePickerFragment(act)
        vm = ViewModelProvider(this).get(DoctorVM::class.java)
        val args: DoctorFragmentArgs by navArgs()
        adapter = DoctorAdapter(ctx)
        rvDoctors.init(ctx)
        vm.pDate = args.pDate.toString()
        vm.pTime = args.pTime.toString()

        tvDate.setText(vm.pDate.displayDate())
        tvTime.setText(vm.pTime.displayTime())

        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onSearchData()
            }
        }
        setObserver()
        initBottom()
        onLoadInitial()
    }

    private fun setObserver() {
        vm.isLoading.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun setListener() {
        adapter.listen = object : DoctorAdapter.ApprovalListener {
            override fun onItemClicked(item: Doctor) {
                popDoctor(item)
            }
        }
        rvDoctors.adapter = adapter
        adapter.addAllItem(vm.cache)


        tvDate.setOnClickListener {
            dateFragment.showDate(tvDate, manager, "", Constants.minDateCurrent)
        }
        tvDate.addTextChangedListener(textWatcher)
        tvTime.setOnClickListener {
            timeFragment.showTime(tvTime, manager, "")
        }
        tvTime.addTextChangedListener(textWatcher)
    }

    override fun removeListener() {

    }

    fun popDoctor(item: Doctor) {
        dialog.show()
        item.image_url?.let {
            layout.findViewById<ImageView>(R.id.ivDoctor)
                .loadProfile(it, getDrawable(R.drawable.ic_doc_placeholder, ctx))
        }
        item.name?.let {
            layout.findViewById<TextView>(R.id.tvDoctorName).text = it
        }
        item.category?.name?.let {
            layout.findViewById<TextView>(R.id.tvDoctorSpesialis).text = it
        }
        item.category?.desc?.let {
            layout.findViewById<TextView>(R.id.tvKeluhanDesc).text = it
        }
        layout.findViewById<Button>(R.id.btRequest).setOnClickListener {
            requestForm(item)
            dialog.dismiss()
        }
    }

    private fun initBottom() {
        layout = layoutInflater.inflate(R.layout.pop_doctor_detail, null)
        dialog = BottomSheetDialog(ctx)
        dialog.setContentView(layout)



        layout.findViewById<ImageView>(R.id.tvClose).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun requestForm(item: Doctor) {
        startFragment(
            DoctorFragmentDirections.actionDoctorFragmentToRequestFragment(
                item,
                pTime = vm.pTime,
                pDate = vm.pDate
            )
        )
    }

    private fun showLoading() {
        llResult.showView()
        slDoctor.startShimmer()
        slDoctor.showView()
        rvDoctors.hideView()
        llEmpty.hideView()
    }

    private fun hideLoading() {
        llResult.showView()
        rvDoctors.showView()
        slDoctor.stopShimmer()
        slDoctor.hideView()
        llEmpty.hideView()
    }

    private fun showEmpty() {
        llResult.hideView()
        llEmpty.showView()
    }

    private fun onLoadInitial() {
        vm.onLoadInitial().observe(viewLifecycleOwner, Observer {
            if (it) {
                onSearchData()
            } else {
                rvDoctors.scrollToPosition(vm.position)
            }
        })
    }

    private fun onSearchData() {
        vm.pDate = convert(tvDate.extract(), Constants.format_date, Constants.format_date_save)
        vm.pTime = convert(tvTime.extract(), Constants.format_time, Constants.format_time_save)
        vm.onLoadData().observe(viewLifecycleOwner, Observer {
            adapter.clear()
            if (it.isNotEmpty()) {
                adapter.setItem(it.toMutableList())
                rvDoctors.scrollToPosition(0)
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
