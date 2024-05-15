package com.example.groceryapp.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groceryapp.R
import com.example.groceryapp.utils.SharedPreferenceClass
import com.example.groceryapp.home.HomeActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //hide navigation and full screen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        Handler(Looper.getMainLooper()).postDelayed(Runnable {

             run {
                 if (SharedPreferenceClass.isLoggedIn(this@SplashActivity)) {
                     val i = Intent(this@SplashActivity, HomeActivity::class.java)
                     startActivity(i)
                     finish()
                 } else {
                     val onboardingIntent= Intent(this,OnboardingActivity::class.java)
                     startActivity(onboardingIntent)
                     finish()
                 }
             }
        }, 3000)
    }
}