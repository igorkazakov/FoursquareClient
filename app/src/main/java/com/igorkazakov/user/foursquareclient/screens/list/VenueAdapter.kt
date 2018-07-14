package com.igorkazakov.user.foursquareclient.screens.list

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.databinding.ItemVenueBinding

class VenueAdapter(val list: List<VenueViewModel>,
                   private val mListener: ListFragment.VenueAdapterListener) : RecyclerView.Adapter<VenueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {

        val binding: ItemVenueBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_venue,
                parent,
                false)

        binding.itemClickListener = mListener

        return VenueViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.binding.venueViewModel = list[position]
        holder.binding.executePendingBindings()
    }
}