package com.example.groceryapp.home.explore

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.category.Category
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.product.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class ExploreViewModel : ViewModel() {

    var liveData: MutableLiveData<ArrayList<Category>> = MutableLiveData<ArrayList<Category>>()

    fun getExploreData(context: Context, fragment: ExploreFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getExploreData()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
//                        Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT)
//                            .show()
                        val type = object : TypeToken<ArrayList<Category?>?>() {}.type
                        val exploreList: ArrayList<Category> =
                            Gson().fromJson(JSONArray(response.body()?.data as ArrayList<*>).toString(), type)

                        liveData.postValue(exploreList)
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun searchCategory(context: Context,txt:String) {
        RetrofitBuilder.build().create(ApiInterface::class.java).searchCategory(txt)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<ArrayList<Category?>?>() {}.type
                        val searchItem: ArrayList<Category> =
                            Gson().fromJson(JSONArray(response.body()?.data as  ArrayList<*>).toString(), type)
                        liveData.postValue(searchItem)
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }





}