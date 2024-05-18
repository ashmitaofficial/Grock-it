package com.example.groceryapp.home.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel : ViewModel() {

    var liveData: MutableLiveData<Any> = MutableLiveData<Any>()

    fun getHomeData(context: Context) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getHomeData()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Logged in Succesfully", Toast.LENGTH_SHORT).show()
                        liveData.postValue(response)

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