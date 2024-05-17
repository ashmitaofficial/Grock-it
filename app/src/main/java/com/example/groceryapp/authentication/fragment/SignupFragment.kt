package com.example.groceryapp.authentication.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.example.groceryapp.utils.Validation.isValidEmail

class SignupFragment : Fragment() {

    private val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var signupBtn: Button
    private lateinit var username_field: EditText
    private lateinit var email_field: EditText
    private lateinit var password_field: EditText
    private lateinit var eye_icon: ImageButton
    private lateinit var already_acc_txt: TextView
    private lateinit var number_field: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        signupBtn = view.findViewById(R.id.signupBtn)
        username_field = view.findViewById(R.id.username_field)
        email_field = view.findViewById(R.id.email_field)
        password_field = view.findViewById(R.id.forgot_pass_txt)
        number_field = view.findViewById(R.id.number_field)
        already_acc_txt = view.findViewById(R.id.already_acc_txt)
        eye_icon = view.findViewById(R.id.eye_icon)

        eye_icon.setOnClickListener {
            //visible password
            if (password_field.inputType == InputType.TYPE_CLASS_TEXT) {
                password_field.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                eye_icon.setImageResource(R.drawable.cut_eye_icon)
            } else {
                password_field.inputType = InputType.TYPE_CLASS_TEXT
                eye_icon.setImageResource(R.drawable.eye_icon)
            }
        }


        signupBtn.setOnClickListener {
            validateData()
        }

        already_acc_txt.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        return view.rootView
    }


    private fun validateData() {
        if (username_field.text.isNullOrEmpty() || email_field.text.isNullOrEmpty() || password_field.text.isNullOrEmpty() || number_field.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "One or more field is empty", Toast.LENGTH_SHORT)
                .show()
        } else {
            //setting data to viewmodel

            authenticationViewmodel.username = username_field.text.toString().trim()
            authenticationViewmodel.email = email_field.text.toString().trim()
            val userEmail: String = email_field.text.toString()
            if (userEmail.isValidEmail() == false) {
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                return
            }
            authenticationViewmodel.password = password_field.text.toString().trim()
            authenticationViewmodel.mobile = number_field.text.toString().trim()

            fragmentManager?.beginTransaction()
                ?.add(R.id.container, LocationFragment::class.java, null)
                ?.addToBackStack(null)
                ?.commit()
        }


    }


}