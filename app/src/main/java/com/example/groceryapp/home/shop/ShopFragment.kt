package com.example.groceryapp.home.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeViewModel

class ShopFragment : Fragment() {

    val homeViewModel: HomeViewModel by viewModels()
     lateinit var progress_bar:ProgressBar

    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        recyclerView = view.findViewById(R.id.shopRecyclerView)
        progress_bar=view.findViewById(R.id.progress_bar)


        progress_bar.visibility= View.VISIBLE
        recyclerView.visibility=View.GONE
        homeViewModel.getHomeData(requireActivity(),this@ShopFragment)

        homeViewModel.liveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = ShopAdapter(it,requireActivity())
            progress_bar.visibility= View.GONE
            recyclerView.visibility=View.VISIBLE
        }
        return view.rootView
    }





}