package com.example.groceryapp.checkout.net_banking

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.checkout.CheckOutViewModel
import com.example.groceryapp.checkout.PaymentMethodAdapter
import com.example.groceryapp.checkout.PaymentWebViewFragment
import org.json.JSONObject


class NetBankingAdapter(val list: ArrayList<Bank>,val context: FragmentActivity,val amount:Int?) :
    RecyclerView.Adapter<NetBankingAdapter.MyViewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_net_banking_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.bank_name.text = list[position].value
        holder.bank_name.setOnClickListener {
            val payload = JSONObject()
            payload.put("amount",amount)
            payload.put("currency","INR")
            payload.put("email", SharedPreferenceClass.getEmail(context))
            payload.put("contact","+917777777777")
            payload.put("method", "netbanking")
            payload.put("bank", list[position].key)
            val bundle = Bundle()
            bundle.putString(AppConstants.PAYLOAD, payload.toString())

            context.supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaymentWebViewFragment::class.java, bundle)
                .commit()
        }
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var bank_name = view.findViewById<TextView>(R.id.bank_name)

    }
}