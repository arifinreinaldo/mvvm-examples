package com.mensa.homecare.customer.base

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.model.data.User
import com.mensa.homecare.customer.util.NavigatorUtil
import com.mensa.homecare.customer.util.confirmDialog
import com.mensa.homecare.customer.util.hideKeyboard
import com.mensa.homecare.customer.util.write

abstract class BaseFragment : Fragment() {
    protected lateinit var ctx: Context
    protected lateinit var act: Activity
    protected lateinit var nav: NavigatorUtil
    protected lateinit var manager: FragmentManager
    protected var saveView: View? = null
    protected lateinit var tagNetworking: String
    abstract fun setLayoutId(): Int
    abstract fun setInitialAsset()
    abstract fun setListener()
    abstract fun removeListener()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (saveView == null) {
            saveView = inflater.inflate(setLayoutId(), container, false)
        }
        return saveView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        write("onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAsset()
        setInitialAsset()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        write("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        write("onStart")
    }

    override fun onResume() {
        super.onResume()
        setListener()
    }

    override fun onPause() {
        super.onPause()
        write("onPause")
        removeListener()
    }

    override fun onStop() {
        super.onStop()
        try {
            hideKeyboard(ctx, view)
        } catch (e: Exception) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        write("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        write("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        write("onDetach")
    }

    private fun setAsset() {
        context?.let {
            ctx = it
        }
        activity?.let {
            act = it
        }
        nav = NavigatorUtil(act)
        fragmentManager?.let {
            manager = it
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes message: Int) {
        Toast.makeText(ctx, getString(message), Toast.LENGTH_SHORT).show()
    }

    fun startFragment(dest: Uri) {
        findNavController().navigate(dest)
    }

    fun startFragment(dest: NavDirections) {
        findNavController().navigate(dest)
    }

    fun startFragment(dest: Int) {
        findNavController().navigate(dest)
    }

    fun previousFragment() {
        findNavController().navigateUp()
    }

    fun forceLogout() {
        LocalValue.is_logged = false
        LocalValue.userData = User(
            null,
            created_at = null,
            device_id = null,
            fcm_token = null,
            gender = null,
            id = null,
            name = null,
            otp_code = null,
            otp_status = null,
            phone_numbers = null,
            role_id = null,
            status = null,
            token = null,
            updated_at = null,
            birth_date = null,
            role_name = null,
            emr_id = null,
            is_active = null,
            family = emptyList(),
            category_id = "0"
        )
        confirmDialog(ctx, getString(R.string.msg_expired), "OK", "") {
            val nav = NavigatorUtil(act)
            nav.toLogin()
            activity?.finish()
        }.show()
    }
}