package com.igorkazakov.user.foursquareclient.screens.venue

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.databinding.ActivityVenueBinding
import com.igorkazakov.user.foursquareclient.screens.base.activity.BaseActivity

class VenueActivity : BaseActivity() {

    private lateinit var activityVenueBinding: ActivityVenueBinding

    companion object {

        private const val EXTRA_OBJECT: String = "EXTRA_OBJECT"

        fun launch(parent: Activity, model: VenueViewModel) {

            val i = Intent(parent, VenueActivity::class.java)
            i.putExtra(EXTRA_OBJECT, model)
            parent.startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityVenueBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_venue)

        setSupportActionBar(activityVenueBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityVenueBinding.activityVenue?.itemVenue?.venueViewModel = intent.extras.getParcelable(EXTRA_OBJECT)
    }
}
