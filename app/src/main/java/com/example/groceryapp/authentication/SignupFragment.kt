package com.example.groceryapp.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel

class SignupFragment : Fragment() {

    private val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var signupBtn: Button
    private lateinit var username_field: TextView
    private lateinit var email_field: TextView
    private lateinit var password_field: TextView
    private lateinit var number_field: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        signupBtn = view.findViewById(R.id.signupBtn)
        username_field = view.findViewById(R.id.username_field)
        email_field = view.findViewById(R.id.email_field)
        password_field = view.findViewById(R.id.password_field)
        number_field = view.findViewById(R.id.number_field)


        signupBtn.setOnClickListener {

            authenticationViewmodel.username = username_field.text.toString()
            authenticationViewmodel.email = email_field.text.toString()
            authenticationViewmodel.password = password_field.text.toString()
            authenticationViewmodel.mobile = number_field.text.toString()

            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, LocationFragment::class.java, null)
                ?.addToBackStack(null)
                ?.commit()


        }


        return view.rootView
    }

}