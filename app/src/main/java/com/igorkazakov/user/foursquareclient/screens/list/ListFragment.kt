package com.igorkazakov.user.foursquareclient.screens.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.databinding.FragmentListBinding
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapFragment
import com.igorkazakov.user.foursquareclient.screens.venue.VenueActivity
import com.igorkazakov.user.foursquareclient.screens.viewModel.ListFragmentViewModel
import com.igorkazakov.user.foursquareclient.screens.viewModel.ViewModelFactory


class ListFragment : BaseMapFragment() {

    interface VenueAdapterListener {
        fun itemClick(model: VenueViewModel)
    }

    private val mVenueAdapterListener = object : VenueAdapterListener {
        override fun itemClick(model: VenueViewModel) {
            showVenueActivity(model)
        }
    }

    private lateinit var fragmentListBinding: FragmentListBinding
    private lateinit var listFragmentViewModel: ListFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        fragmentListBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_list,
                container,
                false)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fragmentListBinding.listView.layoutManager = linearLayoutManager

        listFragmentViewModel = ViewModelProviders.of(this, ViewModelFactory())
                .get(ListFragmentViewModel::class.java)

        locationViewModel.locationLiveData.observe(this, Observer<Location> {

            if (locationViewModel.isLocationChanged(it!!)) {
                showLoading()
                listFragmentViewModel.loadData(it)
            }
        })

        listFragmentViewModel.venuesLiveData.observe(this, Observer {

            showVenues(it!!)
            hideLoading()
        })

        return fragmentListBinding.root
    }

    private fun showVenues(venues: List<VenueViewModel>) {

        val adapter = VenueAdapter(venues, mVenueAdapterListener)
        fragmentListBinding.listView.adapter = adapter
    }

    fun showVenueActivity(model: VenueViewModel) {

        activity?.let {
            VenueActivity.launch(it, model)
        }
    }
}
