package com.mensa.homecare.customer.ui.fragment.home

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.util.DatePickerFragment
import com.mensa.homecare.customer.util.TimePickerFragment
import com.mensa.homecare.customer.util.convert
import com.mensa.homecare.customer.util.extract
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {
    private lateinit var dateFragment: DatePickerFragment
    private lateinit var timeFragment: TimePickerFragment
    private lateinit var textWatcher: TextWatcher
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setInitialAsset() {
        dateFragment = DatePickerFragment(act)
        timeFragment = TimePickerFragment(act)
        validateTime()
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateTime()
            }
        }
    }

    override fun setListener() {
        btSearch.setOnClickListener {
            startFragment(
                HomeFragmentDirections.actionHomeFragmentToDoctorFragment(
                    convert(etDate.extract(), Constants.format_date, Constants.format_date_save),
                    convert(etTime.extract(), Constants.format_time, Constants.format_time_save)
                )
            )
        }
        etDate.setOnClickListener {
            dateFragment.showDate(etDate, manager, "", Constants.minDateCurrent)
        }
        etDate.addTextChangedListener(textWatcher)
        etTime.setOnClickListener {
            timeFragment.showTime(etTime, manager, "")
        }
        etTime.addTextChangedListener(textWatcher)
    }

    fun validateTime() {
        if (etDate.extract().isNotEmpty() && etTime.extract().isNotEmpty()) {
            btSearch.isEnabled = true
        }
    }

    override fun removeListener() {

    }
}
