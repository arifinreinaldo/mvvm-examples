package com.mensa.homecare.customer.ui.fragment.doctor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mensa.homecare.base.BaseRecylerAdapter
import com.mensa.homecare.base.BaseViewHolder
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.model.data.Doctor
import com.mensa.homecare.customer.util.formatCurrency
import com.mensa.homecare.customer.util.getDrawable
import com.mensa.homecare.customer.util.loadProfile
import kotlinx.android.synthetic.main.adapter_doctor.view.*


class DoctorAdapter(val ctx: Context) :
    BaseRecylerAdapter<Doctor, DoctorAdapter.ViewHolder>(ctx) {

    lateinit var listen: ApprovalListener

    interface ApprovalListener {
        fun onItemClicked(item: Doctor)
    }

    class ViewHolder(itemView: View, val listener: ApprovalListener, val ctx: Context) :
        BaseViewHolder<Doctor>(itemView) {

        override fun onBind(item: Doctor) {
            item.image_url?.let {
                itemView.ivDoctor.loadProfile(it, getDrawable(R.drawable.ic_doc_placeholder, ctx))
            }
            item.name?.let {
                itemView.tvDoctorName.text = it
            }
            item.category?.name?.let {
                itemView.tvDoctorSpesialis.text = it
            }
            item.user_hospital.firstOrNull()?.price?.let {
                itemView.tvPrice.text =
                    ctx.getString(R.string.msg_visit_price, formatCurrency(it))
            }
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_doctor, parent), listen, ctx)
    }
}