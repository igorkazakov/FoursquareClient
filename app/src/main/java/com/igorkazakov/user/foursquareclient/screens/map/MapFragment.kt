package com.igorkazakov.user.foursquareclient.screens.map

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.utils.DialogUtils
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils
import javax.inject.Inject


class MapFragment : MvpAppCompatFragment(), MapFragmentInterface, OnMapReadyCallback {

    var mapView: SupportMapFragment? = null
    var map: GoogleMap? = null

    @InjectPresenter
    lateinit var mPresenter: MapFragmentPresenter

    @Inject
    lateinit var mLocationManager: LocationManager

    init {
        MyApplication.appComponent.inject(this)
    }

    @ProvidePresenter
    fun provideMapFragmentPresenter() : MapFragmentPresenter {
        return MapFragmentPresenter(mLocationManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        ButterKnife.bind(this, view)

        mapView = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapView?.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        mPresenter.setMapReady(true)
    }

    override fun showMyLocation(latLng: Location) {
        map?.let {
            val ll = LatLng(latLng.latitude, latLng.longitude)
            it.addMarker(MarkerOptions().position(ll).title("i'm here!"))
            it.moveCamera(CameraUpdateFactory.newLatLng(ll))
        }
    }

    override fun initUpdateLocation() {
        mPresenter.startLocationUpdates(this)
    }

    override fun showLocationError() {
        context?.let {
            DialogUtils.showErrorDialog(it,
                    "Внимание",
                    "Не удалось определить местоположение, включите передачу геоданных")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        if ((requestCode == PermissionUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION ||
                        requestCode == PermissionUtils.REQUEST_CODE_ACCESS_FINE_LOCATION) &&

                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mPresenter.startLocationUpdates(this)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults)
        }
    }
}
