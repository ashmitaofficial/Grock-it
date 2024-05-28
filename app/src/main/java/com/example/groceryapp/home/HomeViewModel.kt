package com.example.groceryapp.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.authentication.fragment.SigninEmailFragment
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.banner.Banner
import com.example.groceryapp.category.Category
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.home.cart.Cart
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductListingFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel : ViewModel() {

    var liveData: MutableLiveData<ArrayList<DynamicItem>> = MutableLiveData<ArrayList<DynamicItem>>()
    var liveDataWishlist: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()
    var shopList = ArrayList<DynamicItem>()
//    var filterLiveData: MutableLiveData<> = MutableLiveData<ArrayList<>>()
    var livedataCart: MutableLiveData<Cart> = MutableLiveData<Cart>()

    fun getHomeData(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getHomeData(SharedPreferenceClass.getEmail(context).toString())
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
//                        Toast.makeText(context, "Logged in Succesfully", Toast.LENGTH_SHORT).show()
                       shopList= createHomeList(response.body()!!)
                        liveData.postValue(shopList)

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

    fun addCart(context: Context, map: HashMap<String,Any>){
        RetrofitBuilder.build().create(ApiInterface::class.java).addCart(map)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<Cart?>() {}.type
                        val cartItems: Cart = Gson().fromJson(
                            JSONObject(response.body()?.data as Map<*,*>).toString(), type
                        )
                        livedataCart.postValue(cartItems)

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


    private fun createHomeList(it: Response): ArrayList<DynamicItem> {
        val homeList = ArrayList<DynamicItem>()
        val type = object : TypeToken<ArrayList<DynamicItem?>?>() {}.type
        val dynamicItemList: ArrayList<DynamicItem> =
            Gson().fromJson(JSONArray(it.data as ArrayList<*>).toString(), type)

        dynamicItemList.forEach { dyItem ->
            when (dyItem.id) {
                "TYPE_BANNER" -> {
                    val typeBanner = object : TypeToken<ArrayList<Banner?>?>() {}.type
                    val bannerList: ArrayList<Banner> =
                        Gson().fromJson(JSONArray(dyItem.data as ArrayList<*>).toString(), typeBanner)
                    homeList.add(DynamicItem(dyItem.id, bannerList))
                }

                "TYPE_LABEL" -> {
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }

                "TYPE_OFFER" -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(JSONArray(dyItem.data as ArrayList<*>).toString(), typeOffer)
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                "TYPE_BEST_SELLER" -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(JSONArray(dyItem.data as ArrayList<*>).toString(), typeOffer)
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                "TYPE_CATEGORIES" -> {
                    val typeCategory = object : TypeToken<ArrayList<Category?>?>() {}.type
                    val categoryList: ArrayList<Category> =
                        Gson().fromJson(JSONArray(dyItem.data as ArrayList<*>).toString(), typeCategory)
                    homeList.add(DynamicItem(dyItem.id, categoryList))
                }

                "TYPE_RECOMMEND" -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(JSONArray(dyItem.data as ArrayList<*>).toString(), typeOffer)
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                "TYPE_SEARCH" -> {
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }
                "TYPE_LOCATION" ->{
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }

                else -> {}
            }

        }
        return homeList
    }

}