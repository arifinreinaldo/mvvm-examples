package com.mensa.homecare.customer.ui.main

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setInitial() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_fragment) as NavHostFragment? ?: return
        val controller = host.navController

        setSupportActionBar(top_bar)
        supportActionBar?.title = ""
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.visitFragment,
                R.id.profileFragment
            )
        )

        bottom_navigation?.setupWithNavController(controller)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.doctorFragment, R.id.requestFragment, R.id.detailFragment -> {
                    hideBottomNav()
                    showActionBar()
                }
                R.id.homeFragment, R.id.visitFragment, R.id.profileFragment -> {
                    hideActionBar()
                    showBottomNav()
                }
                else -> {
                    showBottomNav()
                    showActionBar()
                }
            }
        }

        top_bar.setupWithNavController(controller, appBarConfiguration)
    }

    private fun hideBottomNav() {
        bottom_navigation.visibility = View.GONE
    }

    private fun showBottomNav() {
        bottom_navigation.visibility = View.VISIBLE
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun showActionBar() {
        supportActionBar?.show()
    }

}
