package com.example.groceryapp.home.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R

class ExploreFragment : Fragment() {

    val exploreViewModel: ExploreViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var search_bar: SearchView
    private lateinit var progress_bar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        recyclerView = view.findViewById(R.id.home_explore_recycler_view)
        search_bar = view.findViewById(R.id.search_bar)
        progress_bar = view.findViewById(R.id.progress_bar)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)


        recyclerView.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        exploreViewModel.getExploreData(requireContext(), ExploreFragment())

        exploreViewModel.liveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = ExploreAdapter(it, requireActivity())
            recyclerView.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        }

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    exploreViewModel.getExploreData(requireContext(), this@ExploreFragment)
                }
                return false
            }
        })

        return view.rootView
    }

    fun filterList(text: String) {
        exploreViewModel.searchCategory(requireContext(), text)
    }
}