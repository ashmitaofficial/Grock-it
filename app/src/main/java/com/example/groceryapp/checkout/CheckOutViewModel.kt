package com.example.groceryapp.checkout

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Credentials
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class CheckOutViewModel : ViewModel() {
    var cartTotal: Int? = null
    var liveData: MutableLiveData<ArrayList<PaymentMethodModel>> =
        MutableLiveData<ArrayList<PaymentMethodModel>>()

    var orderLiveData: MutableLiveData<String> = MutableLiveData<String>()

    fun getPaymentMethod(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getPaymentMethod()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<ArrayList<PaymentMethodModel?>?>() {}.type
                        val methodsList: ArrayList<PaymentMethodModel> = Gson().fromJson(
                            JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                        )
                        liveData.postValue(methodsList)

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

    fun createOrder(context: Context, map: HashMap<String, Any?>) {
        PaymentRetrofit.build().create(ApiInterface::class.java).createOrder(Credentials.basic("rzp_test_lXUzSq2Q1wv9tA", "zEkzokeSVxhe2zupKHqBAvLz"), map)
            .enqueue(object : Callback<RazorpayResponse> {
                override fun onResponse(
                    call: Call<RazorpayResponse>,
                    response: retrofit2.Response<RazorpayResponse>
                ) {
                    if (response.body()?.id != null) {
                        val res = response.body()?.id.toString()
                        orderLiveData.postValue(res)
                    }
                }

                override fun onFailure(call: Call<RazorpayResponse>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }

            })
    }

}