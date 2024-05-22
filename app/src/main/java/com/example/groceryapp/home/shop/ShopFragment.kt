package com.example.groceryapp.home.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeViewModel

class ShopFragment : Fragment() {

    val homeViewModel: HomeViewModel by viewModels()

    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        recyclerView = view.findViewById(R.id.shopRecyclerView)

        homeViewModel.getHomeData(requireActivity())

        homeViewModel.liveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = ShopAdapter(it)
        }
        return view.rootView
    }


}