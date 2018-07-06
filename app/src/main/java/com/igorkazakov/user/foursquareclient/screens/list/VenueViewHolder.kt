package com.igorkazakov.user.foursquareclient.screens.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

class VenueViewHolder(itemView: View?, val context: Context) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

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
        nameText.text = model.getName()
        distanceText.text = model.getDistance()
        addressText.text = model.getAddress()
        categoryText.text = model.getCategory()
    }

    override fun onClick(p0: View?) {

    }
}