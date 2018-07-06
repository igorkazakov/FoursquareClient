package com.igorkazakov.user.foursquareclient.screens.list

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
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.REQUEST_CODE_ACCESS_COARSE_LOCATION
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.REQUEST_CODE_ACCESS_FINE_LOCATION
import javax.inject.Inject


class ListFragment : MvpAppCompatFragment(), ListFragmentInterface {

    @BindView(R.id.listView)
    lateinit var list: RecyclerView

    @InjectPresenter
    lateinit var mPresenter: ListFragmentPresenter

    @Inject
    lateinit var mService: DataService

    @Inject
    lateinit var mLocationManager : LocationManager

    init {
        MyApplication.appComponent.inject(this)
    }

    @ProvidePresenter
    fun provideListFragmentPresenter() : ListFragmentPresenter {
        return ListFragmentPresenter(mService, mLocationManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, view)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list.layoutManager = linearLayoutManager

        mPresenter.startLocationUpdates(this)

        return view
    }

    override fun showVenues(venues: List<VenueViewModel>) {
        val adapter = VenueAdapter(venues)
        list.adapter = adapter
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>,
                                   grantResults: IntArray) {

        if ((requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION ||
                        requestCode == REQUEST_CODE_ACCESS_FINE_LOCATION) &&

                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            mPresenter.startLocationUpdates(this)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions,
                    grantResults)
        }
    }
}
