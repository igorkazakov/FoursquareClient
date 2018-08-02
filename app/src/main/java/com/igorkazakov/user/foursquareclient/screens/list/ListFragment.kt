package com.igorkazakov.user.foursquareclient.screens.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.common.ErrorModel
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.databinding.FragmentListBinding
import com.igorkazakov.user.foursquareclient.screens.base.fragment.BaseFragment
import com.igorkazakov.user.foursquareclient.screens.venue.VenueActivity
import com.igorkazakov.user.foursquareclient.screens.viewModel.ListFragmentViewModel
import com.igorkazakov.user.foursquareclient.screens.viewModel.LocationViewModel
import com.igorkazakov.user.foursquareclient.screens.viewModel.ViewModelFactory
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle


class ListFragment : BaseFragment() {

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
    lateinit var locationViewModel: LocationViewModel

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

        locationViewModel = ViewModelProviders.of(this, ViewModelFactory()).get(LocationViewModel::class.java)

        locationViewModel.startLocationUpdates(this)
        locationViewModel.locationErrorLiveData.observe(this, Observer {

            showError(ErrorModel(
                    resources.getString(R.string.location_error_title),
                    resources.getString(R.string.location_error_message)))
        })

        locationViewModel.locationLiveData.observe(this, Observer<Location> {

            if (locationViewModel.isLocationChanged(it!!) || listFragmentViewModel.needData()) {
                showLoading()
                listFragmentViewModel.loadData(it, this)
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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        if ((requestCode == PermissionUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION ||
                        requestCode == PermissionUtils.REQUEST_CODE_ACCESS_FINE_LOCATION) &&

                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            locationViewModel.startLocationUpdates(this)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults)
        }
    }
}
