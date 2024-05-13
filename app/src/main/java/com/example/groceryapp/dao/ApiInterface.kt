package com.example.groceryapp.dao

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("signup")
     fun signup(@Body map:HashMap<String,String>?):Call<Response>
}