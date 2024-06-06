package com.example.groceryapp.checkout.net_banking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.checkout.PaymentMethodAdapter


class NetBankingAdapter : RecyclerView.Adapter<NetBankingAdapter.MyViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_net_banking_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        TODO("Not yet implemented")
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var bank_logo = view.findViewById<ImageView>(R.id.bank_logo)
        var bank_name = view.findViewById<TextView>(R.id.bank_name)

    }
}