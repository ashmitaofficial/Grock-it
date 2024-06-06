package com.example.groceryapp.checkout.debit_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groceryapp.R


class DebitCardFragment : Fragment() {

    var monthArray = arrayOf("Jan", "Feb", "March","April","May","June","July","August","Sept","Oct","Nov","Dec")
    var YearArray = arrayOf("2024", "2025", "2026","2027","2028","2029","2030")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_debit_card, container, false)
        return view.rootView
    }

}