package com.example.groceryapp.home.productList

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductListingFragment
import com.example.groceryapp.product.ProductdetailActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.Objects

class ProductListViewModel:ViewModel() {
    var liveData: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var productDetailLiveData: MutableLiveData<Product> = MutableLiveData<Product>()

    fun getProductListData(context: Context, fragment: ProductListingFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getProducts()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<ArrayList<Product?>?>() {}.type
                        val productList: ArrayList<Product> =
                            Gson().fromJson(
                                JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                            )

                        liveData.postValue(productList)
//                    fragment.loader.visibility = View.GONE
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
//                        fragment.loader.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
//                    fragment.loader.visibility = View.GONE
                }
            })
    }

    fun getProductById( activity: ProductdetailActivity,id:Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getProductById(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(activity, "data fetched successfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<Product?>() {}.type
                        val productDetail: Product =
                            Gson().fromJson(
                                JSONObject(response.body()?.data as Map<*,*>).toString(), type
                            )

                        productDetailLiveData.postValue(productDetail)
//                    fragment.loader.visibility = View.GONE
                    } else {
                        Toast.makeText(activity, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
//                        fragment.loader.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(activity, "Try again", Toast.LENGTH_SHORT).show()
//                    fragment.loader.visibility = View.GONE
                }
            })
    }


    fun getProductByCategory(context: Context, id:Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getProductByCategory(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<ArrayList<Product?>?>()  {}.type
                        val categoryDetail: ArrayList<Product> =
                            Gson().fromJson(
                                JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                            )

                        liveData.postValue(categoryDetail)
//                    fragment.loader.visibility = View.GONE
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
//                        fragment.loader.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
//                    fragment.loader.visibility = View.GONE
                }
            })
    }


}