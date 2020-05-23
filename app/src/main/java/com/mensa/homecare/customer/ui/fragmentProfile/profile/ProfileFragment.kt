package com.mensa.homecare.customer.ui.fragmentProfile.profile

import androidx.fragment.app.Fragment
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.base.BaseFragment
import com.mensa.homecare.customer.local.Constants
import com.mensa.homecare.customer.local.LocalValue
import com.mensa.homecare.customer.util.displayDate
import com.mensa.homecare.customer.util.getDrawable
import com.mensa.homecare.customer.util.loadProfile
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.rgGender
import kotlinx.android.synthetic.main.fragment_profile.tvAddressValue
import kotlinx.android.synthetic.main.fragment_profile.tvBirthdate
import kotlinx.android.synthetic.main.fragment_profile.tvFullName
import kotlinx.android.synthetic.main.fragment_request.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun setInitialAsset() {
        btLogout.setOnClickListener {
            forceLogout()
        }
        ivProfile.loadProfile(
            LocalValue.userData.image_url.toString(), getDrawable(
                when (LocalValue.userData.gender) {
                    Constants.male -> {
                        R.drawable.ic_male_placeholder
                    }
                    Constants.female -> {
                        R.drawable.ic_female_placeholder
                    }
                    else -> R.drawable.ic_male_placeholder
                }, ctx
            )
        )
        tvFullName.text = LocalValue.userData.name ?: LocalValue.userData.nik ?: ""
        tvBirthdate.text = LocalValue.userData.birth_date?.displayDate()
        var gender = -1
        if (LocalValue.userData.gender ?: "" == Constants.male) {
            gender = 0
        } else if (LocalValue.userData.gender ?: "" == Constants.female) {
            gender = 1
        }
        if (gender >= 0) {
            rgGender.check(
                rgGender.getChildAt(gender).id
            )
        }
        tvAddressValue.text = LocalValue.userData.address ?: ""
    }

    override fun setListener() {

    }

    override fun removeListener() {
    }

}
