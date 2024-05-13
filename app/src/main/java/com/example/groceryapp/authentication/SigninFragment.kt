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


class SigninFragment : Fragment() {


    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var newuser_link:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_signin, container, false)
        newuser_link=view.findViewById(R.id.newuser_link)

        newuser_link.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container,SignupFragment::class.java,null)
                ?.addToBackStack(null)
                ?.commit()
        }

        return view.rootView
    }

}