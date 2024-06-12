package com.example.groceryapp.checkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.checkout.debit_card.DebitCardFragment
import com.example.groceryapp.checkout.net_banking.NetBankingFragment
import com.example.groceryapp.checkout.upi.UPIFragment
import com.example.groceryapp.checkout.wallet.WalletFragment

class PaymentMethodAdapter(private val list: ArrayList<PaymentMethodModel>, val context: PaymentMethodFragment)
    : RecyclerView.Adapter<PaymentMethodAdapter.MyViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_payment_option_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.payment_txt.text = list[position].title

        holder.payment_txt.setOnClickListener {
            when (list[position].payment_code) {
                AppConstants.DEBIT_CARD -> {
                    context.fragmentManager?.beginTransaction()
                        ?.add(R.id.container, DebitCardFragment::class.java, null)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                AppConstants.UPI -> {
                    context.fragmentManager?.beginTransaction()
                        ?.add(R.id.container, UPIFragment::class.java, null)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                AppConstants.WALLET -> {
                    context.fragmentManager?.beginTransaction()
                        ?.add(R.id.container, WalletFragment::class.java, null)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                AppConstants.NET_BANKING -> {
                    context.fragmentManager?.beginTransaction()
                        ?.add(R.id.container, NetBankingFragment::class.java, null)
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
        }
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var payment_txt = view.findViewById<TextView>(R.id.payment_txt)
    }
}