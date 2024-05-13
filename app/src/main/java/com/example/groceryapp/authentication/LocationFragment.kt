package com.example.groceryapp.authentication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale


class LocationFragment : Fragment() {

    private lateinit var checkLocationPermission: ActivityResultLauncher<Array<String>>
    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var fetch_btn: Button
    private lateinit var submit_btn: Button
    private lateinit var geocoder: Geocoder
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var zone_txt: EditText
    private lateinit var area_txt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // user allow or deny location permission callback.
        checkLocationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                getLastLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Need permission to move forward",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        fetch_btn = view.findViewById(R.id.fetch_btn)
        submit_btn = view.findViewById(R.id.submit_btn)
        zone_txt = view.findViewById(R.id.zone_txt)
        area_txt = view.findViewById(R.id.area_txt)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())


        fetch_btn.setOnClickListener {
            getLastLocation()
        }

        submit_btn.setOnClickListener {
            val map = HashMap<String, String>()
            map["countryCode"] = authenticationViewmodel.countryCode ?: ""
            map["mobile"] = authenticationViewmodel.mobile ?: ""
            map["email"] = authenticationViewmodel.email ?: ""
            map["username"] = authenticationViewmodel.username ?: ""
            map["password"] = authenticationViewmodel.password ?: ""
            map["address"] = authenticationViewmodel.address ?: ""
            authenticationViewmodel.signup(requireActivity(), map)
        }
        return view.rootView
    }

    private fun getLastLocation() {
        if (checkLocationPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    if (it.result != null) {
                        geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val addressList =
                            geocoder.getFromLocation(it.result.latitude, it.result.longitude, 1)
                        val city: String? = addressList?.get(0)?.locality
                        val address: String? = addressList?.get(0)?.getAddressLine(0)
                        authenticationViewmodel.address = city + " " + address
                        zone_txt.setText(city)
                        area_txt.setText(address)
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please turn on your location...",
                    Toast.LENGTH_LONG
                ).show()
                val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(locationIntent)
            }
        } else {
            requestPermissionLocation()
        }


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

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}