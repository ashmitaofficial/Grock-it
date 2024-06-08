package com.example.groceryapp.authentication.fragment

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.base.utils.Validation.isValidEmail
import me.ibrahimsn.lib.CirclesLoadingView

class ForgotPasswordFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirm_password: EditText
    private lateinit var resetBtn: Button
    private lateinit var eyeIcon_password: ImageButton
    private lateinit var eyeIcon_confirm_password: ImageButton
    public lateinit var loader: CirclesLoadingView
    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        email = view.findViewById(R.id.email_field)
        password = view.findViewById(R.id.pass_field_forgot)
        confirm_password = view.findViewById(R.id.confrim_password_field)
        resetBtn = view.findViewById(R.id.reset_btn)
        loader = view.findViewById(R.id.loader)
        eyeIcon_password = view.findViewById(R.id.eye_icon_password)
        eyeIcon_confirm_password = view.findViewById(R.id.eye_icon_confirm_password)

        eyeIcon_password.setOnClickListener {
            if (password.inputType == InputType.TYPE_CLASS_TEXT) {
                password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                eyeIcon_password.setImageResource(R.drawable.cut_eye_icon)
            } else {
                password.inputType = InputType.TYPE_CLASS_TEXT
                eyeIcon_password.setImageResource(R.drawable.eye_icon)
            }
        }

        eyeIcon_confirm_password.setOnClickListener {
            if (confirm_password.inputType == InputType.TYPE_CLASS_TEXT) {
                confirm_password.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                eyeIcon_confirm_password.setImageResource(R.drawable.cut_eye_icon)
            } else {
                confirm_password.inputType = InputType.TYPE_CLASS_TEXT
                eyeIcon_confirm_password.setImageResource(R.drawable.eye_icon)
            }
        }

        resetBtn.setOnClickListener {
            validateData()
        }
        return view.rootView
    }

    private fun validateData() {
        if (email.text.isNullOrEmpty() || password.text.isNullOrEmpty() || confirm_password.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "One or more field is empty", Toast.LENGTH_SHORT)
                .show()
        } else {
            val userEmail: String = email.text.toString()
            if (userEmail.isValidEmail() == false) {
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                return
            }
            val user_password: String = password.text.toString()
            val user_new_password: String = confirm_password.text.toString()
            if (!user_password.equals(user_new_password)) {
                Toast.makeText(requireContext(), "New password is not matching from old password", Toast.LENGTH_SHORT).show()
                return
            }
            loader.visibility = View.VISIBLE
            val map = HashMap<String, String>()
            map.put(AppConstants.EMAIL, email.text.toString())
            map.put(AppConstants.PASSWORD, confirm_password.text.toString())
            authenticationViewmodel.forgot(requireActivity(), map, this)

        }


    }


}