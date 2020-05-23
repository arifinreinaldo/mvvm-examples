package com.mensa.homecare.customer.ui.otp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import com.mensa.homecare.customer.R
import kotlinx.android.synthetic.main.view_otp.view.*

class OTPLibrary
@JvmOverloads
constructor(
    context: Context,
    atributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, atributeSet, defStyle) {
    private lateinit var arrayEditText: Array<EditText>
    lateinit var listener: OTPListener

    init {
        LayoutInflater.from(context).inflate(R.layout.view_otp, this, true)
        initListener()
    }

    interface OTPListener {
        fun onSuccess(otp: String)
        fun onError()
    }

    fun initListener() {
        arrayEditText = arrayOf(
            etOTP1, etOTP2, etOTP3, etOTP4, etOTP5, etOTP6
        )
        addTextListener(etOTP1, etOTP2, etOTP1)
        addTextListener(etOTP1, etOTP3, etOTP2)
        addTextListener(etOTP2, etOTP4, etOTP3)
        addTextListener(etOTP3, etOTP5, etOTP4)
        addTextListener(etOTP4, etOTP6, etOTP5)
        addTextListener(prev = etOTP5, cur = etOTP6)

        etOTP6.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateOTPFinal()
            }
            true
        }
    }

    fun validateOTP(): String {
        var rtn: String
        var text = ""
        arrayEditText.forEach {
            it.let {
                text += it.text
            }
        }
        if (text.length == 6) {
            rtn = text
        } else {
            rtn = ""
        }
        return rtn
    }

    fun validateOTPFinal() {
        val otp = validateOTP()
        if (!otp.isNullOrEmpty()) {
            listener.onSuccess(otp)
            hideKeyboard(context, etOTP6)
        } else {
            listener.onError()
        }
    }

    fun addTextListener(prev: EditText, next: EditText? = null, cur: EditText) {
        cur.addTextChangedListener(OTPWatcher(prev, next, cur))
        cur.setOnKeyListener(OTPKeyListener(prev, next, cur))
    }

    inner class OTPKeyListener(
        val prev: EditText,
        val next: EditText? = null,
        val cur: EditText
    ) : OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                val text = cur.text.toString()
                if (text.isNullOrEmpty()) {
                    prev.requestFocus()
                }
            }
            return false
        }

    }

    inner class OTPWatcher(
        val prev: EditText,
        val next: EditText? = null,
        val cur: EditText
    ) :
        TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s.let { et ->
                if (et.toString().length == 0) {
                    prev.requestFocus()

                } else {
                    next?.let {
                        it.requestFocus()
                    }
                }
                if (cur.id == R.id.etOTP6) {
                    validateOTPFinal()
                } else {
                    validateOTP()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (it.length > 1) {
                    cur.setText(s.toString().get(start).toString())
                }
            }
        }

    }

    fun hideKeyboard(context: Context?, view: View?) {
        context?.let { ctx ->
            val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            view?.let {
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}

