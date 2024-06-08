package com.example.groceryapp.authentication.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groceryapp.authentication.model.UserModel
import com.example.groceryapp.authentication.fragment.ForgotPasswordFragment
import com.example.groceryapp.authentication.fragment.LocationFragment
import com.example.groceryapp.authentication.fragment.OtpFragment
import com.example.groceryapp.authentication.fragment.SigninEmailFragment
import com.example.groceryapp.authentication.fragment.SigninFragment
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.home.HomeActivity
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class AuthenticationViewmodel : ViewModel() {
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var mobile: String? = null
    var countryCode: String? = "+91"
    var address: String? = null
    var otp: Int? = 0


    var liveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun signup(context: Activity, map: HashMap<String, String>?, fragment: LocationFragment) {

        RetrofitBuilder.build().create(ApiInterface::class.java).signup(map)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                        val typeUser = object : TypeToken<UserModel?>() {}.type
                        val user: UserModel = Gson().fromJson(
                            JSONObject(response.body()?.data as LinkedTreeMap<*, *>).toString(), typeUser)
                        SharedPreferenceClass.savedLogin(context, user.email.toString())
                        fragment.loader.visibility = View.GONE
                        val homeIntent = Intent(context, HomeActivity::class.java)
                        context.startActivity(homeIntent)
                        context.finish()

                    } else {
                        fragment.loader.visibility = View.GONE
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    fragment.loader.visibility = View.GONE
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()

                }
            })
    }

    fun login(context: Activity, email: String, password: String, fragment: SigninEmailFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).login(email, password)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                        SharedPreferenceClass.savedLogin(context, email)
                        fragment.loader.visibility = View.GONE
                        val homeIntent = Intent(context, HomeActivity::class.java)
                        context.startActivity(homeIntent)
                        context.finish()
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE

                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.loader.visibility = View.GONE

                }
            })

    }

    fun getOtp(context: Activity, number: String, fragment: SigninFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getOtp(number)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Otp sent", Toast.LENGTH_SHORT).show()
                        fragment.loader.visibility = View.GONE
                        val res = response.body()?.data as Double
                        liveData.postValue(res.toInt())

                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE

                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.loader.visibility = View.GONE
                }
            })
    }

    fun resendOtp(context: Activity, number: String, fragment: OtpFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getOtp(number)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Otp sent", Toast.LENGTH_SHORT).show()
                        fragment.loader.visibility = View.GONE
                        val res = response.body()?.data as Double
                        liveData.postValue(res.toInt())

                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE

                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.loader.visibility = View.GONE
                }
            })
    }


    fun loginGoogle(context: Activity, email: String, username: String, fragment: SigninFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).loginGoogle(email, username)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        SharedPreferenceClass.savedLogin(context, email)
                        val homeIntent = Intent(context, HomeActivity::class.java)
                        context.startActivity(homeIntent)
                        context.finish()
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.loader.visibility = View.GONE
                }
            })

    }

    fun loginMobile(context: Activity, mobile: String, countryCode: String, fragment: OtpFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java)
            .loginMobile(mobile.substring(3), countryCode)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        SharedPreferenceClass.savedLogin(context, mobile)
                        fragment.loader.visibility = View.GONE
                        val homeIntent = Intent(context, HomeActivity::class.java)
                        context.startActivity(homeIntent)
                        context.finish()
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    fragment.loader.visibility = View.GONE
                }
            })

    }

    fun forgot(context: Activity, map: HashMap<String, String>?, fragment: ForgotPasswordFragment) {

        RetrofitBuilder.build().create(ApiInterface::class.java).forgot(map)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        Toast.makeText(context, "Password reset Succesfully", Toast.LENGTH_SHORT)
                            .show()
                        fragment.loader.visibility = View.GONE
                    } else {
                        fragment.loader.visibility = View.GONE
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    fragment.loader.visibility = View.GONE
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }



}