package com.example.groceryapp.onboarding

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groceryapp.R
import com.example.groceryapp.authentication.activity.AuthenticationActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var checkLocationPermission: ActivityResultLauncher<Array<String>>
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

            if (checkLocationPermission())
            {
                if(isGpsEnabled()) {
                    val signinIntent = Intent(this, AuthenticationActivity::class.java)
                    startActivity(signinIntent)
                    finish()
                }
                else{
                    openGps()
                }
            } else {
                requestPermissionLocation()
            }
        }

        //user allowed/denied permission
        checkLocationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                if (!isGpsEnabled()) {
                    openGps()
                }
            } else {
                Toast.makeText(this, "Need permission to move forward", Toast.LENGTH_SHORT).show()
            }
        }

        //permission enabled by default or not
        if (!checkLocationPermission()) {
            requestPermissionLocation()
        } else {
            if (!isGpsEnabled()) {
                openGps()
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private fun requestPermissionLocation() {
        // take location permission
        checkLocationPermission.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    //GPS enabled or not
    private fun isGpsEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun openGps() {
        Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG).show()
        val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(locationIntent)
    }
}