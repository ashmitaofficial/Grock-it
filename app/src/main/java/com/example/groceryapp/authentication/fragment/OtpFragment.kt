package com.example.groceryapp.authentication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.example.groceryapp.utils.SharedPreferenceClass
import com.example.groceryapp.home.HomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class OtpFragment : Fragment() {

    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var forwardBtn: FloatingActionButton
    private lateinit var otp_field: EditText
    private lateinit var backBtn:ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        forwardBtn = view.findViewById(R.id.forwardBtn)
        otp_field = view.findViewById(R.id.otp_field)
        backBtn= view.findViewById(R.id.backBtn)

        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }


        forwardBtn.setOnClickListener {
            val otp = otp_field.text.toString().trim().toInt()
            if (otp == authenticationViewmodel.otp) {
                SharedPreferenceClass.savedLogin(requireContext())
                val forwardIntent = Intent(this.requireContext(), HomeActivity::class.java)
                startActivity(forwardIntent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Enter valid OTP", Toast.LENGTH_SHORT).show()
            }
        }

        return view.rootView
    }
}