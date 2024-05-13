package com.example.groceryapp.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel

class LoginFragment : Fragment() {

    private val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var email:TextView
    private lateinit var password:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}