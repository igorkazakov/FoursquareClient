package com.igorkazakov.user.foursquareclient.screens.splashscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.igorkazakov.user.foursquareclient.R
import com.igorkazakov.user.foursquareclient.screens.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Thread {

            Thread.sleep(2000)
            MainActivity.launch(this, R.id.action_list)

        }.start()
    }
}
