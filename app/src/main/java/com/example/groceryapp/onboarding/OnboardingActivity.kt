package com.example.groceryapp.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groceryapp.R
import com.example.groceryapp.authentication.AuthenticationActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var getStartedBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        getStartedBtn = findViewById(R.id.getStartedBtn)

        getStartedBtn.setOnClickListener {
            val signinIntent = Intent(this, AuthenticationActivity::class.java)
            startActivity(signinIntent)
            finish()
        }

    }
}