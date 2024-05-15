package com.example.groceryapp.utils

import android.content.Context

object SharedPreferenceClass {

 public fun savedLogin(context: Context)
{
    val sharedPreferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isLoggedIn", true)
    editor.apply()
}

  public  fun isLoggedIn(context: Context): Boolean {
        val preferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        return preferences.getBoolean("isLoggedIn", false)
    }

}