package com.example.groceryapp.checkout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeActivity

class OrderAcceptedFragment : Fragment() {
    lateinit var back_home_txt:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_order_accepted, container, false)
        back_home_txt=view.findViewById(R.id.back_home_txt)

        back_home_txt.setOnClickListener {
            val homeIntent= Intent(requireContext(),HomeActivity::class.java)
            requireActivity().startActivity(homeIntent)
            requireActivity().finish()

        }
        return view.rootView
    }

}