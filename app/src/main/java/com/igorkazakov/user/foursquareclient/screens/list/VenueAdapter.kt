package com.igorkazakov.user.foursquareclient.screens.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

class VenueAdapter(val list: List<VenueViewModel>,
                   private val mListener: VenueAdapterListener?) : RecyclerView.Adapter<VenueViewHolder>() {

    interface VenueAdapterListener {
        fun venueAdapterItemClick(model: VenueViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {

        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_venue,
                parent,
                false)

        return VenueViewHolder(view, object : VenueViewHolder.VenueViewHolderListener {
            override fun viewHolderClick(model: VenueViewModel) {
                mListener?.venueAdapterItemClick(model)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(list[position])
    }
}