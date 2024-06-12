package com.example.groceryapp.dao

data class Response (val status:Int?,val data:Any?,val error:Error)

data class Error(val msg:String?,val code:String?)

