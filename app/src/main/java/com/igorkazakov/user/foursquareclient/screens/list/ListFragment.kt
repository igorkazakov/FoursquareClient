package com.igorkazakov.user.foursquareclient.screens.list

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.interactors.ShowVenuesOnMapInteractor
import com.igorkazakov.user.foursquareclient.screens.base.fragment.BaseFragment
import com.igorkazakov.user.foursquareclient.screens.venue.VenueActivity
import com.igorkazakov.user.foursquareclient.utils.DialogUtils
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils
import javax.inject.Inject


class ListFragment : BaseFragment(), ListFragmentInterface {

    @BindView(R.id.listView)
    lateinit var list: RecyclerView

    @InjectPresenter
    lateinit var mPresenter: ListFragmentPresenter

    @Inject
    lateinit var mService: DataService

    @Inject
    lateinit var mLocationManager: LocationManager

    @Inject
    lateinit var mShowVenuesOnMapInteractor: ShowVenuesOnMapInteractor

    init {
        MyApplication.appComponent.inject(this)
    }

    @ProvidePresenter
    fun provideListFragmentPresenter(): ListFragmentPresenter {
        return ListFragmentPresenter(mService, mLocationManager, mShowVenuesOnMapInteractor)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, view)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list.layoutManager = linearLayoutManager

        return view
    }

    override fun showVenues(venues: List<VenueViewModel>) {

        val adapter = VenueAdapter(venues, object : VenueAdapter.VenueAdapterListener {
            override fun venueAdapterItemClick(model: VenueViewModel) {
                mPresenter.showVenueDetail(model)
            }
        })

        list.adapter = adapter
    }

    override fun showVenueActivity(model: VenueViewModel) {

        activity?.let {
            VenueActivity.launch(it, model)
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
