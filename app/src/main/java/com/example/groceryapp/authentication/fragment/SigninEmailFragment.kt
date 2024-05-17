package com.example.groceryapp.authentication.fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.example.groceryapp.utils.Validation.isValidEmail
import me.ibrahimsn.lib.CirclesLoadingView
import java.util.regex.Matcher
import java.util.regex.Pattern


class SigninEmailFragment : Fragment() {

    private val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login_btn: Button
    public lateinit var loader: CirclesLoadingView
    private lateinit var accountTxt: TextView
    private lateinit var eye_icon: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin_email, container, false)

        email = view.findViewById(R.id.email_field)
        password = view.findViewById(R.id.pass_field)
        login_btn = view.findViewById(R.id.login_btn)
        loader = view.findViewById(R.id.loader)
        accountTxt = view.findViewById(R.id.already_acc_txt)
        eye_icon = view.findViewById(R.id.eye_icon)

        eye_icon.setOnClickListener {
            eye_icon.setOnClickListener {
                //visible password
                if (password.inputType == InputType.TYPE_CLASS_TEXT) {
                    password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                    eye_icon.setImageResource(R.drawable.cut_eye_icon)
                } else {
                    password.inputType = InputType.TYPE_CLASS_TEXT
                    eye_icon.setImageResource(R.drawable.eye_icon)
                }
            }
        }


        login_btn.setOnClickListener {
            val userEmail: String = email.text.toString()
            if (userEmail.isValidEmail() == false) {
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userPassword: String = password.text.toString().trim()
            loader.visibility = View.VISIBLE
            authenticationViewmodel.login(requireActivity(), userEmail, userPassword, this)
        }

        accountTxt.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.add(R.id.container, SignupFragment::class.java, null)
                ?.addToBackStack(null)
                ?.commit()
        }

        return view.rootView
    }


}