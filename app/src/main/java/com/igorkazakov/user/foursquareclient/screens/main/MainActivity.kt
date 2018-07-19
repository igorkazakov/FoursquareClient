package com.igorkazakov.user.foursquareclient.screens.main

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.databinding.ActivityMainBinding
import com.igorkazakov.user.foursquareclient.screens.base.activity.BaseActivity
import com.igorkazakov.user.foursquareclient.screens.list.ListFragment
import com.igorkazakov.user.foursquareclient.screens.map.MapFragment


class MainActivity : BaseActivity() {

    private var mCurrentAction = 0

    companion object {

        private const val EXTRA_ACTION: String = "EXTRA_ACTION"

        fun launch(parent: Activity, action: Int) {

            val intent = Intent(parent, MainActivity::class.java)
            intent.putExtra(EXTRA_ACTION, action)
            parent.startActivity(intent)
        }
    }

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(activityMainBinding.toolbar)

        activityMainBinding.contentLayout?.bottomNavigation?.setOnNavigationItemSelectedListener {

            changeFragment(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        if (savedInstanceState == null) {
            changeFragment(intent.getIntExtra(EXTRA_ACTION, 0))
        } else {
            changeFragment(savedInstanceState.getInt(EXTRA_ACTION, 0))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(EXTRA_ACTION, mCurrentAction)
    }

    private fun changeFragment(id: Int) {

        when (id) {
            R.id.action_list -> showOrCreateFragment(ListFragment::class.java)
            R.id.action_map -> showOrCreateFragment(MapFragment::class.java)
        }

        mCurrentAction = id
    }

    private fun showOrCreateFragment(fragmentClass: Class<*>) {

        var fragmentExist = false
        val fragmentManager = supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {

            if (fragment.javaClass.toString().equals(fragmentClass.toString(), true)) {
                fragmentExist = true

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.show(fragment).commit()
                fragment.onResume()

            } else {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.hide(fragment).commit()
            }
        }

        if (!fragmentExist) {
            try {
                val fragment = fragmentClass.newInstance() as Fragment
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.frame_layout_fragments, fragment).commit()

            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }
}
