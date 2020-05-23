package com.mensa.homecare.customer.ui.splash

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.mensa.homecare.customer.BuildConfig
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseActivity
import com.mensa.homecare.customer.local.Constants.INITIAL_REQUEST
import com.mensa.homecare.customer.util.PermissionUtil.INITIAL_PERMS
import com.mensa.homecare.customer.util.PermissionUtil.hasPermissions
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), SplashView {
    private lateinit var presenter: SplashPresenter
    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setInitial() {
        presenter = SplashPresenter(this)
        tvVersion.text = getString(R.string.splash_version, BuildConfig.VERSION_NAME)
        if (!hasPermissions(*INITIAL_PERMS)) {
            ActivityCompat.requestPermissions(act, INITIAL_PERMS, INITIAL_REQUEST)
        } else {
            presenter.nextScreen()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var granted = true
        if (requestCode == INITIAL_REQUEST) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()) {
                for (i in INITIAL_PERMS.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false
                    }
                }
            }
            if (!granted) {
                showMessage(getString(R.string.err_permission))
            } else {
                presenter.nextScreen()
            }
        }
    }

    override fun toLogin() {
        nav.toLogin()
    }

    override fun toMain() {
        nav.toMain()
    }

}
