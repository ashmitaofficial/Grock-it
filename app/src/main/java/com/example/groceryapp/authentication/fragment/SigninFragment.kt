package com.example.groceryapp.authentication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import me.ibrahimsn.lib.CirclesLoadingView


class SigninFragment : Fragment() {


    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var newuser_link: TextView
    private lateinit var email_text: TextView
    private lateinit var signin_number_text: EditText
    private lateinit var signin_number_btn: ImageButton
    public lateinit var loader: CirclesLoadingView


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        newuser_link = view.findViewById(R.id.newuser_link)
        email_text = view.findViewById(R.id.email_text)
        signin_number_text = view.findViewById(R.id.sign_in_edit_text)
        signin_number_btn = view.findViewById(R.id.signin_number_btn)
        loader= view.findViewById(R.id.loader)


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

        newuser_link.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SignupFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }

        email_text.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SigninEmailFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }

        signin_number_btn.setOnClickListener {
                validateData()
        }

        return view.rootView
    }

    private fun validateData() {
        if (signin_number_text.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show()

        } else {
            val mobileNum = signin_number_text.text.toString().trim()
            loader.visibility= View.VISIBLE
            authenticationViewmodel.getOtp(requireActivity(), mobileNum,this)
        }
    }

    override fun onStop() {
        super.onStop()
        authenticationViewmodel.liveData.postValue(0)
    }

}