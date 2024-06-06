package com.example.groceryapp.checkout

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.home.filterBottomSheet.FilterAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class CheckOutViewModel : ViewModel() {
    var liveData: MutableLiveData<ArrayList<PaymentMethod>> =
        MutableLiveData<ArrayList<PaymentMethod>>()

     fun getPaymentMethod(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getPaymentMethod()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        val type = object : TypeToken<ArrayList<PaymentMethod?>?>() {}.type
                        val methodsList: ArrayList<PaymentMethod> = Gson().fromJson(
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

}