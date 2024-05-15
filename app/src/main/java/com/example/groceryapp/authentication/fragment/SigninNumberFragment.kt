package com.example.groceryapp.authentication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.ibrahimsn.lib.CirclesLoadingView


class SigninNumberFragment : Fragment() {

    private lateinit var enter_num_txt: EditText
    private lateinit var moveForward_btn: FloatingActionButton
    private lateinit var backBtn: ImageView
    public lateinit var loader: CirclesLoadingView
    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_signin__number_, container, false)

        enter_num_txt = view.findViewById(R.id.otp_field)
        moveForward_btn = view.findViewById(R.id.moveForward_btn)
        backBtn = view.findViewById(R.id.backBtn)
        loader=view.findViewById(R.id.loader)

        moveForward_btn.setOnClickListener {
            validateData()
        }

        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        authenticationViewmodel.liveData.observe(
            viewLifecycleOwner
        ) {
            if (it > 0) {
                authenticationViewmodel.otp = it
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, OtpFragment::class.java, null)
                    .addToBackStack(null)
                    .commit()
            }
        }
        return view.rootView
    }
    private fun validateData() {
        if (enter_num_txt.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show()

        } else {
            val mobileNum = enter_num_txt.text.toString().trim()
            loader.visibility= View.VISIBLE
//            authenticationViewmodel.getOtp(requireActivity(), mobileNum,this)
        }
    }
    override fun onStop() {
        super.onStop()
        authenticationViewmodel.liveData.postValue(0)
    }


}