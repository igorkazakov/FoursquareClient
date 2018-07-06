package com.igorkazakov.user.foursquareclient.screens.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.screens.list.ListFragment
import com.igorkazakov.user.foursquareclient.screens.map.MapFragment
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx


class MainActivity : MvpAppCompatActivity(), MainActivityInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.bottom_navigation)
    lateinit var bottomNavigation: BottomNavigationViewEx

    @InjectPresenter
    lateinit var mPresenter: MainActivityPresenter

    @ProvidePresenter
    fun provideLoginPresenter() : MainActivityPresenter {
        return MainActivityPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        bottomNavigation.setOnNavigationItemSelectedListener {

            changeFragment(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        showOrCreateFragment(ListFragment::class.java)
    }

    private fun changeFragment(id: Int) {

        when (id) {
            R.id.action_list -> showOrCreateFragment(ListFragment::class.java)
            R.id.action_map -> showOrCreateFragment(MapFragment::class.java)
        }
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
