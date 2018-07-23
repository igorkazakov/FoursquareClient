package com.igorkazakov.user.foursquareclient.screens.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.databinding.FragmentMapBinding
import com.igorkazakov.user.foursquareclient.screens.base.fragment.BaseFragment

import com.igorkazakov.user.foursquareclient.screens.viewModel.MapFragmentVewModel
import com.igorkazakov.user.foursquareclient.screens.viewModel.ViewModelFactory


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private var mapView: SupportMapFragment? = null
    private var map: GoogleMap? = null
    private lateinit var mapFragmentViewModel: MapFragmentVewModel
    private lateinit var fragmentMapBinding: FragmentMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        fragmentMapBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_map,
                container,
                false)

        mapView = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapView?.getMapAsync(this)

        mapFragmentViewModel = ViewModelProviders.of(this, ViewModelFactory())
                .get(MapFragmentVewModel::class.java)

        return fragmentMapBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        map?.let {
            it.setMinZoomPreference(13f)
            val uiSettings = it.uiSettings
            uiSettings.isZoomControlsEnabled = true
        }

        mapFragmentViewModel.venuesLiveData.observe(this, Observer {

            showVenuesOnMap(it!!)
            hideLoading()
        })
    }

    private fun showMyLocation(latLng: Location) {
        map?.let {

            val ny = LatLng(latLng.latitude, latLng.longitude)
            val markerOptions = MarkerOptions()
            markerOptions.position(ny)
            it.addMarker(markerOptions)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(ny, 17f)
            it.animateCamera(cameraUpdate)
        }
    }

    private fun showVenuesOnMap(model: VenueMapModel) {

        map?.let {

            it.clear()

            showMyLocation(model.location)

            if (model.venues.isNotEmpty()) {

                val builder = LatLngBounds.Builder()

                model.venues.forEach { venue: Venue ->

                    val latitude = venue.location?.lat
                    val longitude = venue.location?.lng

                    if (latitude != null && longitude != null) {

                        val ll = LatLng(latitude, longitude)
                        builder.include(ll)

                        val markerOptions = MarkerOptions()
                        markerOptions.position(ll)
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_venue_marker_icon))
                        markerOptions.title(venue.name)
                        it.addMarker(markerOptions)
                    }
                }

                val width = resources.displayMetrics.widthPixels
                val height = resources.displayMetrics.heightPixels
                val padding = (width * 0.12).toInt()

                val cameraUpdate = CameraUpdateFactory
                        .newLatLngBounds(builder.build(), width, height, padding)
                it.moveCamera(cameraUpdate)

            }
        }
    }
}
