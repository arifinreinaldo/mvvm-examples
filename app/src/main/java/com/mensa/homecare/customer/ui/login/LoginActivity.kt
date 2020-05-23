package com.mensa.homecare.customer.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseActivity
import com.mensa.homecare.customer.util.disableFunction
import com.mensa.homecare.customer.util.enableFunction
import com.mensa.homecare.customer.util.extract
import com.mensa.homecare.customer.util.hideKeyboard
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginView {
    private lateinit var presenter: LoginPresenter
    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setInitial() {
        presenter = LoginPresenter(this)
        btVerify.disableFunction(ctx)
        btVerify.setOnClickListener { presenter.validateNumber(etPhone.extract()) }

        etPhone.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.toString().length >= 9) {
                        btVerify.enableFunction(ctx)
                    } else {
                        btVerify.disableFunction(ctx)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        etPhone.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.validateNumber(etPhone.extract())
            }
            true
        }
    }

    override fun hideKeyboard() {
        hideKeyboard(ctx, etPhone)
    }

    override fun toOTP(phone: String) {
        nav.toOTP("0$phone")
    }
}
