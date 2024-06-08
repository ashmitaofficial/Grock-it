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
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.home.cart.Cart
import com.example.groceryapp.home.shop.ShopFragment
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductListingFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel : ViewModel() {

    var liveData: MutableLiveData<ArrayList<DynamicItem>> =
        MutableLiveData<ArrayList<DynamicItem>>()
    var shopList = ArrayList<DynamicItem>()
    fun getHomeData(context: Context, fragment: ShopFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java)
            .getHomeData(SharedPreferenceClass.getEmail(context).toString())
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        shopList = createHomeList(response.body()!!)
                        liveData.postValue(shopList)
                        fragment.progress_bar.visibility = View.GONE
                        fragment.recyclerView.visibility = View.VISIBLE

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
                AppConstants.BANNER -> {
                    val typeBanner = object : TypeToken<ArrayList<Banner?>?>() {}.type
                    val bannerList: ArrayList<Banner> =
                        Gson().fromJson(
                            JSONArray(dyItem.data as ArrayList<*>).toString(),
                            typeBanner
                        )
                    homeList.add(DynamicItem(dyItem.id, bannerList))
                }

                AppConstants.LABEL -> {
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }

                AppConstants.OFFER -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(
                            JSONArray(dyItem.data as ArrayList<*>).toString(),
                            typeOffer
                        )
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                AppConstants.BEST_SELLER -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(
                            JSONArray(dyItem.data as ArrayList<*>).toString(),
                            typeOffer
                        )
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                AppConstants.CATEGORIES -> {
                    val typeCategory = object : TypeToken<ArrayList<Category?>?>() {}.type
                    val categoryList: ArrayList<Category> =
                        Gson().fromJson(
                            JSONArray(dyItem.data as ArrayList<*>).toString(),
                            typeCategory
                        )
                    homeList.add(DynamicItem(dyItem.id, categoryList))
                }

                AppConstants.RECOMMEND -> {
                    val typeOffer = object : TypeToken<ArrayList<Product?>?>() {}.type
                    val offerList: ArrayList<Product> =
                        Gson().fromJson(
                            JSONArray(dyItem.data as ArrayList<*>).toString(),
                            typeOffer
                        )
                    homeList.add(DynamicItem(dyItem.id, offerList))
                }

                AppConstants.SEARCH -> {
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }

                AppConstants.LOCATION -> {
                    homeList.add(DynamicItem(dyItem.id, dyItem.data))
                }

                else -> {}
            }

        }
        return homeList
    }

}