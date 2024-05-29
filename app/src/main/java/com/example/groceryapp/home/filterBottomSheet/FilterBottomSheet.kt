package com.example.groceryapp.home.filterBottomSheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.home.productList.ProductListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class FilterBottomSheet(val selectedFilterItem: (String)-> Unit):BottomSheetDialogFragment() {
    val viewmodel: ProductListViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var applyBtn: Button
    lateinit var progress_bar: ProgressBar
    lateinit var linearLayout: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_recycler_view,container,false)
        recyclerView=view.findViewById(R.id.filter_recycler_view)
        applyBtn = view.findViewById(R.id.filter_btn)
        linearLayout=view.findViewById(R.id.linear_layout)
        progress_bar=view.findViewById(R.id.progress_bar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(),  RecyclerView.VERTICAL, false)

        linearLayout.visibility=View.GONE
        progress_bar.visibility=View.VISIBLE
        getFilterItems(requireContext())

        applyBtn.setOnClickListener {
            val list = FilterAdapter.selectedItems
            var text = ""
            list.forEach {
                text = text + it + ","
            }
            selectedFilterItem(text)
            FilterAdapter.selectedItems.clear()
            dismiss()
        }

        return view?.rootView
    }


    private fun getFilterItems(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getFilterItems()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<ArrayList<String?>?>() {}.type
                        val filterListData: ArrayList<String> = Gson().fromJson(
                            JSONArray(response.body()?.data as ArrayList<*>).toString(), type)
                        recyclerView.adapter = FilterAdapter(filterListData, requireActivity())
                        linearLayout.visibility=View.VISIBLE
                        progress_bar.visibility=View.GONE

                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
//                        progress_bar.visibility=View.GONE
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
//                    progress_bar.visibility=View.GONE
                }
            })
    }

}