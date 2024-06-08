package com.example.groceryapp.authentication.fragment

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import me.ibrahimsn.lib.CirclesLoadingView
import java.util.Locale


class LocationFragment : Fragment() {

    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var submit_btn: Button
    private lateinit var geocoder: Geocoder
    private lateinit var zone_txt: EditText
    private lateinit var area_txt: EditText
   private lateinit var mLocationRequest: LocationRequest
   private lateinit var mFusedLocationClient:FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var backBtn: ImageView
    public lateinit var loader: CirclesLoadingView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        submit_btn = view.findViewById(R.id.payBtn)
        zone_txt = view.findViewById(R.id.zone_field)
        area_txt = view.findViewById(R.id.area_field)
        backBtn=view.findViewById(R.id.backBtn)
        loader=view.findViewById(R.id.loader)


        getLastLocation()

        submit_btn.setOnClickListener {
            val map = HashMap<String, String>()
            map["countryCode"] = authenticationViewmodel.countryCode ?: ""
            map["mobile"] = authenticationViewmodel.mobile ?: ""
            map["email"] = authenticationViewmodel.email ?: ""
            map["username"] = authenticationViewmodel.username ?: ""
            map["password"] = authenticationViewmodel.password ?: ""
            map["address"] = authenticationViewmodel.address ?: ""

            loader.visibility= View.VISIBLE
            authenticationViewmodel.signup(requireActivity(), map,this)

        }

        backBtn.setOnClickListener{
            requireActivity().onBackPressed()
        }
        return view.rootView
    }

    private fun getLastLocation() {
        if (checkLocationPermission()) {

            // create location request
            mLocationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build()

            // callback for location
             mLocationCallback= object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        if (location != null) {
                            geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val addressList =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            val city: String? = addressList?.get(0)?.locality
                            val address: String? = addressList?.get(0)?.getAddressLine(0)
                            authenticationViewmodel.address = city + " " + address
                            zone_txt.setText(city)
                            area_txt.setText(address)
                            break
                        }
                    }
                }
            }
            // call location request
           mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireContext())
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)

        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    override fun onDestroy() {
        super.onDestroy()
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)

    }

}