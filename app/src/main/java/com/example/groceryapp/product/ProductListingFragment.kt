package com.example.groceryapp.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.home.filterBottomSheet.FilterBottomSheet
import com.example.groceryapp.home.productList.ProductListViewModel

class ProductListingFragment : Fragment() {

    val viewmodel: ProductListViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var filter_icon: ImageView
    lateinit var add_btn: ImageButton
    lateinit var search_bar: SearchView
    lateinit var bottomSheet: FilterBottomSheet
    lateinit var progressBar: ProgressBar
    lateinit var linearLayout: LinearLayout
    var originalList: List<Product> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val cid = this.arguments?.getInt(AppConstants.CATEGORY_ID, -1)
        val searchItem=this.arguments?.getString(AppConstants.SEARCHED_ITEM,"")

        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        filter_icon = view.findViewById(R.id.filter_icon)
        recyclerView = view.findViewById(R.id.product_list_recycler_view)
        search_bar = view.findViewById(R.id.search_bar)
        progressBar= view.findViewById(R.id.progress_bar)
        linearLayout= view.findViewById(R.id.linear_layout)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        filter_icon.setOnClickListener {
//            recyclerView.visibility=View.GONE

//            viewmodel.getFilterItems(requireContext())
            val bottomSheet = FilterBottomSheet(selectedFilterItem = {
                filterList(it)
            })
            bottomSheet.show(requireActivity().supportFragmentManager, "ModalBottomSheet")
        }


        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    viewmodel.getProductListData(requireContext(), this@ProductListingFragment)
                }
                return false
            }
        })

        viewmodel.liveData.observe(viewLifecycleOwner) {
            recyclerView.adapter = ProductAdapter(it, requireActivity())
        }



        if (cid == null) {
            progressBar.visibility=View.VISIBLE
            linearLayout.visibility=View.GONE
            viewmodel.getProductListData(requireContext(), this)
        }else if(!searchItem.isNullOrEmpty()){
            viewmodel.searchItem(requireContext(),searchItem)
        }
        else {
            viewmodel.getProductByCategory(requireContext(), cid)
        }

        return view.rootView
    }

    fun filterList(text: String) {
        viewmodel.searchItem(requireContext(), text)
    }


}


