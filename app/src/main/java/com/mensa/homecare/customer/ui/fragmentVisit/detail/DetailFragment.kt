package com.mensa.homecare.customer.ui.fragmentVisit.detail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.util.displayDate
import com.mensa.homecare.customer.util.formatCurrency
import com.mensa.homecare.customer.util.getDrawable
import com.mensa.homecare.customer.util.loadProfile
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun setInitialAsset() {
        val args: DetailFragmentArgs by navArgs()
        val data = args.visitData
        data.visit_time?.let {
            val split = it.split(",")
            if (split.size == 2) {
                tvVisitDateValue.text = split.firstOrNull()
                tvVisitTimeValue.text = split[1]
            }
        }
        data.patient?.let {
            etFullName.setText(it.name)
            etBirthdate.setText(it.birth_date?.displayDate())
            tvAddressValue.setText(it.address.toString())
        }
        data.assignment?.firstOrNull()?.let { assign ->
            assign.user?.let { user ->
                tvDoctorName.text = user.name.toString()
                ivDoctor.loadProfile(
                    user.image_url.toString(),
                    getDrawable(R.drawable.ic_doc_placeholder, ctx)
                )
                var gender = -1
                if (user.gender ?: "" == Constants.male) {
                    gender = 0
                } else if (user.gender ?: "" == Constants.female) {
                    gender = 1
                }
                if (gender >= 0) {
                    rgGender.check(
                        rgGender.getChildAt(gender).id
                    )
                }
            }

            tvPaymentTotalValue.text = formatCurrency(assign.price)
        }
    }

    override fun setListener() {
    }

    override fun removeListener() {
    }

}
