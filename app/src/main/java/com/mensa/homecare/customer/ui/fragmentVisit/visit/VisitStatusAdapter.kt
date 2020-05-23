package com.mensa.homecare.customer.ui.fragmentVisit.visit

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mensa.homecare.base.BaseRecylerAdapter
import com.mensa.homecare.base.BaseViewHolder
import com.mensa.homecare.customer.R
import com.mensa.homecare.customer.model.data.Status
import kotlinx.android.synthetic.main.adapter_status.view.*


class VisitStatusAdapter(val ctx: Context) :
    BaseRecylerAdapter<Status, VisitStatusAdapter.ViewHolder>(ctx) {
    lateinit var listen: ApprovalListener

    interface ApprovalListener {
        fun onItemClicked(item: Status, position: Int)
    }

    class ViewHolder(itemView: View, val listener: ApprovalListener, val ctx: Context) :
        BaseViewHolder<Status>(itemView) {

        override fun onBind(item: Status) {
            itemView.tvVisitStatus.text = item.status
            if (item.selected) {
                itemView.setBackgroundColor(
                    ctx.resources.getColor(R.color.colorPrimaryDark)
                )
                itemView.tvVisitStatus.setTextColor(ctx.resources.getColor(R.color.colorWhite))
            } else {
                itemView.setBackgroundColor(ctx.resources.getColor(R.color.colorWhite))
                itemView.tvVisitStatus.setTextColor(ctx.resources.getColor(R.color.colorTextInput))
            }
            itemView.setOnClickListener {
                listener.onItemClicked(item, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflate(R.layout.adapter_status, parent), listen, ctx)
    }
}