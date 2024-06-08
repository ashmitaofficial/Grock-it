package com.example.groceryapp.checkout.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.checkout.PaymentWebViewFragment
import com.example.groceryapp.checkout.net_banking.Bank
import org.json.JSONObject

class WalletAdapter(val list: ArrayList<Wallet>, val context: FragmentActivity, val amount:Int?): RecyclerView.Adapter<WalletAdapter.MyViewholder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallet_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.wallet_name.text=list[position].wallet

        holder.wallet_name.setOnClickListener {
            val payload = JSONObject()
            payload.put("amount",amount)
            payload.put("currency","INR")
            payload.put("email", SharedPreferenceClass.getEmail(context))
            payload.put("contact","+917777777777")
            payload.put("method", "wallet")
            payload.put("wallet", list[position].wallet)
            val bundle = Bundle()
            bundle.putString(AppConstants.PAYLOAD, payload.toString())

            context.supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaymentWebViewFragment::class.java, bundle)
                .commit()
        }
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view){
//        var wallet_logo = view.findViewById<ImageView>(R.id.wallet_logo)
        var wallet_name = view.findViewById<TextView>(R.id.wallet_name)

    }
}