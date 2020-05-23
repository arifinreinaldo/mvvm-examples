package com.mensa.homecare.customer.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.mensa.homecare.customer.util.NavigatorUtil

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var ctx: Context
    protected lateinit var nav: NavigatorUtil
    protected lateinit var act: Activity
    abstract fun setLayoutId(): Int
    abstract fun setInitial()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        setDefaultSetting()
        setInitial()
    }

    fun setDefaultSetting() {
        ctx = this
        act = this
        nav = NavigatorUtil(act)
    }

    fun showMessage(message: String) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes message: Int) {
        Toast.makeText(ctx, getString(message), Toast.LENGTH_SHORT).show()
    }
}