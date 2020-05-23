package com.mensa.homecare.customer.util

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mensa.homecare.customer.local.Constants
import java.util.*

class DatePickerFragment(val act: Activity) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var editDate: EditText
    private val format = Constants.format_date
    private lateinit var cal: Calendar
    private var minDate: Long = -1

    fun showDate(editText: EditText, fragment: FragmentManager?, tag: String, minimum: Long = -1) {
        cal = Calendar.getInstance()
        if (!this.isAdded) {
            editDate = editText
            fragment?.let {
                show(fragment, tag)
            }
        }
        if (minimum != (-1).toLong()) {
            minDate = minimum
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        cal.set(year, month, day)
        val date = cal.time
        editDate.setText(printDate(date, format))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date: String = editDate.text.toString()
        var year: Int
        var month: Int
        var day: Int
        if (date.isEmpty()) {
            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)
            day = cal.get(Calendar.DAY_OF_MONTH)
        } else {
            val pointerDate = parseDate(date, format)
            cal.time = pointerDate
            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)
            day = cal.get(Calendar.DAY_OF_MONTH)
        }
        val datePickerDialog = DatePickerDialog(act, this, year, month, day)
        if (minDate != (-1).toLong()) {
            datePickerDialog.datePicker.minDate = minDate
        }
        return datePickerDialog
    }
}

class TimePickerFragment(val act: Activity) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        editDate.setText(String.format("%02d:%02d", hourOfDay, minute))
    }

    private lateinit var editDate: EditText

    fun showTime(editText: EditText, fragment: FragmentManager?, tag: String) {
        if (!this.isAdded) {
            editDate = editText
            fragment?.let {
                show(fragment, tag)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date: String = editDate.text.toString()
        if (date.isEmpty()) {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                true
            )

        } else {
            val splitDate = date.split(":")
            val hour = splitDate[0].toInt()
            val minute = splitDate[1].toInt()
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                true
            )
        }

    }
}