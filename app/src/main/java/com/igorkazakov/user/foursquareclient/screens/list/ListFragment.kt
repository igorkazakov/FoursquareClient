package com.igorkazakov.user.foursquareclient.screens.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.databinding.FragmentListBinding
import com.igorkazakov.user.foursquareclient.screens.base.fragment.BaseFragment
import com.igorkazakov.user.foursquareclient.screens.venue.VenueActivity
import com.igorkazakov.user.foursquareclient.screens.viewModel.ListFragmentViewModel
import com.igorkazakov.user.foursquareclient.screens.viewModel.ViewModelFactory


class ListFragment : BaseFragment() {//BaseMapFragment(), ListFragmentInterface {


    interface VenueAdapterListener {
        fun itemClick(model: VenueViewModel)
    }

    private val mVenueAdapterListener = object : VenueAdapterListener {
        override fun itemClick(model: VenueViewModel) {
            showVenueActivity(model)
        }
    }

    @BindView(R.id.listView)
    lateinit var listView: RecyclerView

    //@InjectPresenter
    //lateinit var mPresenter: ListFragmentPresenter

    //@Inject
    //lateinit var mService: DataService

    //@Inject
    //lateinit var mLocationManager: LocationManager

//    init {
//        MyApplication.appComponent.inject(this)
//    }

    //@ProvidePresenter
    //fun provideListFragmentPresenter(): ListFragmentPresenter {
    //    return ListFragmentPresenter(mService, mLocationManager)
    //}

    //override fun createPresenter(): BaseMapPresenter<*> {
    //    return mPresenter
    //}

    lateinit var viewModel: ListFragmentViewModel
    //lateinit var binding: FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, view)

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = linearLayoutManager

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(ListFragmentViewModel::class.java)
        viewModel.startLocationUpdates(this)
        viewModel.mLocationLiveData.observe(this, Observer<List<VenueViewModel>> {

            showVenues(it!!)
        })

        return view
    }

    private fun showVenues(venues: List<VenueViewModel>) {

        val adapter = VenueAdapter(venues, mVenueAdapterListener)
        listView.adapter = adapter
    }

    fun showVenueActivity(model: VenueViewModel) {

        activity?.let {
            VenueActivity.launch(it, model)
        }
    }
}
