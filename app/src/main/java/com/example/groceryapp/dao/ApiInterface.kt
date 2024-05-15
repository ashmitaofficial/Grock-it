package com.example.groceryapp.dao

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("signup")
    fun signup(@Body map: HashMap<String, String>?): Call<Response>

    @GET("login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<Response>
    @GET("otp")
    fun getOtp(@Query("mobile") mobile: String): Call<Response>
}