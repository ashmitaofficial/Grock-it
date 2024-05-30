package com.example.groceryapp.home.productList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.R
import com.example.groceryapp.authentication.fragment.LocationFragment
import com.example.groceryapp.authentication.model.UserModel
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.home.cart.Cart
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductAdapter
import com.example.groceryapp.product.ProductListingFragment
import com.example.groceryapp.product.ProductdetailActivity
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.Objects

class ProductListViewModel : ViewModel() {
    var liveData: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var addBasketLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var productDetailLiveData: MutableLiveData<Product> = MutableLiveData<Product>()
    var wishlistLivedata: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var cartLivedata: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var cartLivedataMinus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var filterLivedata: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    lateinit var progress_bar: ProgressBar
    fun getProductListData(context: Context, fragment: ProductListingFragment) {

        RetrofitBuilder.build().create(ApiInterface::class.java).getProducts()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
//                        Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<ArrayList<Product?>?>() {}.type
                        val productList: ArrayList<Product> =
                            Gson().fromJson(
                                JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                            )

                        liveData.postValue(productList)
                        fragment.progressBar.visibility = View.GONE
                        fragment.linearLayout.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.linearLayout.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }


    fun getProductById(activity: ProductdetailActivity, id: Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getProductById(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<Product?>() {}.type
                        val productDetail: Product =
                            Gson().fromJson(
                                JSONObject(response.body()?.data as Map<*, *>).toString(), type
                            )

                        productDetailLiveData.postValue(productDetail)
                        activity.progress_bar.visibility = View.GONE
                        activity.ScrollView.visibility = View.VISIBLE

                    } else {
                        Toast.makeText(activity, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        activity.progress_bar.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(activity, "Try again", Toast.LENGTH_SHORT).show()
                    activity.progress_bar.visibility = View.GONE
                }
            })
    }


    fun getProductByCategory(context: Context, id: Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getProductByCategory(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
//                        Toast.makeText(context, "data fetched successfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<ArrayList<Product?>?>() {}.type
                        val categoryDetail: ArrayList<Product> =
                            Gson().fromJson(
                                JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                            )

                        liveData.postValue(categoryDetail)
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

    fun addWishlist(context: Context, wishId: Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).addWishlist(wishId)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(
                            context,
                            "Item added successfully in wishlist",
                            Toast.LENGTH_SHORT
                        ).show()
                        wishlistLivedata.postValue(true)
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

    fun removeWishlist(context: Context, id: Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).removeWishlist(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(
                            context,
                            "Item remove successfully from wishlist",
                            Toast.LENGTH_SHORT
                        ).show()
                        wishlistLivedata.postValue(true)
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

    fun searchItem(context: Context, search: String) {
        RetrofitBuilder.build().create(ApiInterface::class.java).searchItem(search)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<ArrayList<Product?>?>() {}.type
                        val searchItem: ArrayList<Product> =
                            Gson().fromJson(
                                JSONArray(response.body()?.data as ArrayList<*>).toString(),
                                type
                            )
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

    fun removeCartMinus(context: Context, id: Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).removeCartMinus(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        cartLivedataMinus.postValue(true)

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

    fun addCart(context: Context, map: HashMap<String, Any>) {
        RetrofitBuilder.build().create(ApiInterface::class.java).addCart(map)
            .enqueue(object : Callback<Response> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        cartLivedata.postValue(true)
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

    fun addBasket(context: Context, map: HashMap<String, Any>, activity: ProductdetailActivity) {
        RetrofitBuilder.build().create(ApiInterface::class.java).addCart(map)
            .enqueue(object : Callback<Response> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        addBasketLiveData.postValue(true)
                        activity.progress_bar.visibility = View.GONE
                        activity.ScrollView.visibility = View.VISIBLE
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


    fun getFilterItems(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getFilterItems()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val res = response.body()?.data as List<String>
                        filterLivedata.postValue(res)

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