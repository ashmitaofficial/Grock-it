package com.example.groceryapp.checkout.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.checkout.CheckOutViewModel

class WalletFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val checkOutViewModel: CheckOutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.wallet_item, container, false)
        return view.rootView
    }

}