package com.mensa.homecare.customer.ui.splash

import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.util.simpleCountDown


class SplashPresenter(val listener: SplashView) {
    fun nextScreen() {
        simpleCountDown(1.0, 1.0, {
            if (LocalValue.is_logged) {
                listener.toMain()
            } else {
                listener.toLogin()
            }
        }, {
        }).start()

    }
}