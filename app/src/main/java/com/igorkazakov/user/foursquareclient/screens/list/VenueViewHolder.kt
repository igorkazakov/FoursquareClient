package com.igorkazakov.user.foursquareclient.screens.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

class VenueViewHolder(itemView: View?,
                      private val mListener: VenueViewHolderListener?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

    interface VenueViewHolderListener {
        fun viewHolderClick(model: VenueViewModel)
    }

    lateinit var mModel: VenueViewModel

    @BindView(R.id.nameText)
    lateinit var nameText: TextView
    @BindView(R.id.distanceText)
    lateinit var distanceText: TextView
    @BindView(R.id.addressText)
    lateinit var addressText: TextView
    @BindView(R.id.categoryText)
    lateinit var categoryText: TextView

    init {
        itemView?.let {
            it.setOnClickListener(this)
            ButterKnife.bind(this, it)
        }
    }

    fun bind(model: VenueViewModel) {

        mModel = model
        nameText.text = model.name
        distanceText.text = model.distance
        addressText.text = model.address
        categoryText.text = model.category
    }

    override fun onClick(p0: View?) {
        mListener?.viewHolderClick(mModel)
    }
}