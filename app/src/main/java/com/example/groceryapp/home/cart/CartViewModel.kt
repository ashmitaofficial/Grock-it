package com.example.groceryapp.home.cart

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.product.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class CartViewModel : ViewModel() {
    var livedata: MutableLiveData<Cart> = MutableLiveData<Cart>()
    var total: Double? = 0.0
    var cgst: String? = ""
    var sgst: String? = ""

    fun getCart(context: Context,fragment: CartFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getCart()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200 && response.body()?.data != null) {
                        val type = object : TypeToken<Cart?>() {}.type
                        val cartItems: Cart= Gson().fromJson(
                            JSONObject(response.body()?.data as Map<*,*>).toString(), type
                        )
                        livedata.postValue(cartItems)
                        fragment.constraintLayout.visibility= View.VISIBLE
                        fragment.progress_bar.visibility= View.GONE

                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.progress_bar.visibility= View.GONE
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.progress_bar.visibility= View.GONE
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
                        val cartItems: Cart= Gson().fromJson(
                            JSONObject(response.body()?.data as Map<*,*>).toString(), type
                        )
                        livedata.postValue(cartItems)

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

    fun removeItem(context: Context,id:Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).removeCartItem(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<Cart?>() {}.type
                        val cartItems: Cart= Gson().fromJson(
                            JSONObject(response.body()?.data as Map<*,*>).toString(), type
                        )
                        livedata.postValue(cartItems)

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

    fun removeCartMinus(context: Context,id:Int) {
        RetrofitBuilder.build().create(ApiInterface::class.java).removeCartMinus(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<Cart?>() {}.type
                        val cartItems: Cart= Gson().fromJson(
                            JSONObject(response.body()?.data as Map<*,*>).toString(), type
                        )
                        livedata.postValue(cartItems)

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