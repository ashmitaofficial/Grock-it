package com.example.groceryapp.authentication.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.groceryapp.authentication.AuthenticationActivity
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback

class AuthenticationViewmodel:ViewModel() {
     var username:String?=null
     var email:String?=null
     var password:String?=null
     var mobile:String?=null
     var countryCode:String?="+91"
     var address:String?=null

    fun signup(context: Activity, map:HashMap<String,String>?)
    {

        RetrofitBuilder.build().create(ApiInterface::class.java).signup(map).enqueue(object :Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if(response.body()?.status==200)
                {
                    val homeIntent=Intent(context,AuthenticationActivity::class.java)
                    context.startActivity(homeIntent)
                    context.finish()

                }else{
                    Toast.makeText(context,response.body()?.error?.msg,Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(context,"Try again",Toast.LENGTH_SHORT).show()

            }
        })
    }

}