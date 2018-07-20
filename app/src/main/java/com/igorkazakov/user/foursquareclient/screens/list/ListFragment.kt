package com.igorkazakov.user.foursquareclient.screens.list

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
import com.igorkazakov.user.foursquareclient.interactors.LocationInteractor
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapFragment
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapPresenter
import com.igorkazakov.user.foursquareclient.screens.venue.VenueActivity
import javax.inject.Inject


class ListFragment : BaseMapFragment(), ListFragmentInterface {

    @BindView(R.id.listView)
    lateinit var list: RecyclerView

    @InjectPresenter
    lateinit var mPresenter: ListFragmentPresenter

    @Inject
    lateinit var mService: DataService

    @Inject
    lateinit var mLocationManager: LocationManager

    @Inject
    lateinit var mLocationInteractor: LocationInteractor

    init {
        MyApplication.appComponent.inject(this)
    }

    @ProvidePresenter
    fun provideListFragmentPresenter(): ListFragmentPresenter {
        return ListFragmentPresenter(mService, mLocationManager, mLocationInteractor)
    }

    override fun createPresenter(): BaseMapPresenter<*> {
        return mPresenter
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
}
