package com.example.groceryapp.home.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeViewModel
import com.example.groceryapp.home.shop.ShopAdapter

class ExploreFragment : Fragment() {

    val exploreViewModel: ExploreViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        recyclerView = view.findViewById(R.id.home_explore_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)


        exploreViewModel.getHomeData(requireContext(), ExploreFragment())

        exploreViewModel.liveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = ExploreAdapter(it,requireActivity())
        }

        return view.rootView
    }

}