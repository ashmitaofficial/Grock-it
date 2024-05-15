package com.example.groceryapp.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import me.ibrahimsn.lib.CirclesLoadingView

class SigninEmailFragment : Fragment() {

    private val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login_btn: Button
    public lateinit var loader: CirclesLoadingView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin_email, container, false)

        email = view.findViewById(R.id.email_field)
        password = view.findViewById(R.id.pass_field)
        login_btn = view.findViewById(R.id.login_btn)
        loader=view.findViewById(R.id.loader)


        login_btn.setOnClickListener {
            val userEmail: String = email.text.toString().trim()
            val userPassword: String = password.text.toString().trim()
            loader.visibility=View.VISIBLE
            authenticationViewmodel.login(requireActivity(), userEmail, userPassword,this)
        }

        return view.rootView
    }


}