package com.example.groceryapp.checkout.wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R

class WalletAdapter: RecyclerView.Adapter<WalletAdapter.MyViewholder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallet_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        TODO("Not yet implemented")
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view){
        var wallet_logo = view.findViewById<ImageView>(R.id.wallet_logo)
        var wallet_name = view.findViewById<TextView>(R.id.wallet_name)

    }
}