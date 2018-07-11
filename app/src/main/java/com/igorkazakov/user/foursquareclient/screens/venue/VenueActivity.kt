package com.igorkazakov.user.foursquareclient.screens.venue

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

class VenueActivity : MvpAppCompatActivity(), VenueInterface {

    @BindView(R.id.nameText)
    lateinit var nameText: TextView
    @BindView(R.id.distanceText)
    lateinit var distanceText: TextView
    @BindView(R.id.addressText)
    lateinit var addressText: TextView
    @BindView(R.id.categoryText)
    lateinit var categoryText: TextView
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @InjectPresenter
    lateinit var mPresenter: VenuePresenter

    @ProvidePresenter
    fun provideVenuePresenter() : VenuePresenter {
        return VenuePresenter()
    }

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
        setContentView(R.layout.activity_venue)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.setupView(intent.extras.getParcelable(EXTRA_OBJECT))
    }

    override fun initView(model: VenueViewModel) {
        nameText.text = model.name
        distanceText.text = model.distance
        addressText.text = model.address
        categoryText.text = model.category
    }
}
