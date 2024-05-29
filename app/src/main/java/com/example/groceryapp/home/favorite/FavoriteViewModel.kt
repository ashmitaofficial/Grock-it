package com.example.groceryapp.home.favorite

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.category.Category
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.product.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class FavoriteViewModel : ViewModel() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    var livedata: MutableLiveData<ArrayList<Product>> = MutableLiveData<ArrayList<Product>>()

    fun getWishList(context: Context,fragment: FavoriteFragment) {
        RetrofitBuilder.build().create(ApiInterface::class.java).getWishList()
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
//                        Toast.makeText(context, "Logged in Succesfully", Toast.LENGTH_SHORT).show()
                        val type = object : TypeToken<ArrayList<Product?>?>() {}.type
                        val getWishlist: ArrayList<Product> = Gson().fromJson(
                            JSONArray(response.body()?.data as ArrayList<*>).toString(), type
                        )
                        livedata.postValue(getWishlist)
                        fragment.progress_bar.visibility= View.GONE
                        fragment.recyclerView.visibility= View.VISIBLE

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