package com.mensa.homecare.customer.ui.fragmentVisit.visit

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mensa.homecare.base.BaseRecylerAdapter
import com.mensa.homecare.base.BaseViewHolder
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.model.response.Visit
import com.mensa.homecare.customer.util.formatCurrency
import com.mensa.homecare.customer.util.getDrawable
import com.mensa.homecare.customer.util.loadProfile
import kotlinx.android.synthetic.main.adapter_visit.view.*


class VisitAdapter(val ctx: Context) :
    BaseRecylerAdapter<Visit, VisitAdapter.ViewHolder>(ctx) {

    lateinit var listen: ApprovalListener

    interface ApprovalListener {
        fun onItemClicked(item: Visit, position: Int)
    }

    class ViewHolder(itemView: View, val listener: ApprovalListener, val ctx: Context) :
        BaseViewHolder<Visit>(itemView) {

        override fun onBind(item: Visit) {
            itemView.tvStatus.text = ""
            itemView.tvDoctorName.text = ""
            itemView.tvVisitFee.text = ""
            itemView.tvVisitDate.text = ""
            itemView.ivDoctor.loadProfile(R.drawable.ic_doc_placeholder, null)
            itemView.tvDoctorSpesialis.text = ""

            itemView.tvStatus.text = item.status_name
            item.assignment?.firstOrNull()?.let { assign ->
                assign.user?.let { user ->
                    itemView.tvDoctorName.text = user.name.toString()
                    itemView.ivDoctor.loadProfile(
                        user.image_url.toString(),
                        getDrawable(R.drawable.ic_doc_placeholder, ctx)
                    )
                }
                itemView.tvVisitFee.text = formatCurrency(assign.price)
            }
            itemView.tvDoctorSpesialis.text = adapterPosition.toString()
            val split = item.visit_time?.split(",")
            if (split?.size == 2) {
                itemView.tvVisitDate.text = split.first()
            }
            itemView.setOnClickListener {
                listener.onItemClicked(item, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_visit, parent), listen, ctx)
    }
}