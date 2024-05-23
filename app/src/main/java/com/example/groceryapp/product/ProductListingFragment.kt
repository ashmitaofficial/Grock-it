package com.example.groceryapp.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants

import com.example.groceryapp.home.productList.ProductListViewModel

class ProductListingFragment : Fragment() {

    val viewmodel: ProductListViewModel by viewModels()
    lateinit var recyclerView:RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val cid= this.arguments?.getInt(AppConstants.CATEGORY_ID,-1)

        val view= inflater.inflate(R.layout.fragment_product_list, container, false)
        recyclerView=view.findViewById(R.id.product_list_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)


        viewmodel.liveData.observe(viewLifecycleOwner){
            recyclerView.adapter= ProductListAdapter(it)
        }
        if(cid==null){
        viewmodel.getProductListData(requireContext(),this)}
        else{
            viewmodel.getProductByCategory(requireContext(),cid)
        }

        return view.rootView
    }

}