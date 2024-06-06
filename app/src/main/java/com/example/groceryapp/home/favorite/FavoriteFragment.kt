package com.example.groceryapp.home.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.explore.ExploreAdapter
import com.example.groceryapp.home.explore.ExploreFragment
import com.example.groceryapp.home.explore.ExploreViewModel

class FavoriteFragment : Fragment() {

    val favoriteViewModel: FavoriteViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var progress_bar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.fav_recycler_view)
        progress_bar = view.findViewById(R.id.progress_bar)

        progress_bar.visibility=View.VISIBLE
        recyclerView.visibility=View.GONE
        favoriteViewModel.getWishList(requireContext(),this)

        favoriteViewModel.livedata.observe(viewLifecycleOwner){
            recyclerView.adapter = FavoriteAdapter(it,requireActivity())
        }


        return view.rootView
    }

}