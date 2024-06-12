package com.example.groceryapp.checkout.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.checkout.CheckOutViewModel
import com.example.groceryapp.checkout.net_banking.Bank
import com.example.groceryapp.checkout.net_banking.NetBankingAdapter
import com.razorpay.PaymentMethodsCallback
import com.razorpay.Razorpay
import org.json.JSONObject

class WalletFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val checkOutViewModel: CheckOutViewModel by activityViewModels()
    lateinit var razorpay: Razorpay
    lateinit var progress_bar: ProgressBar
    lateinit var linear_layout: LinearLayout
    var list = ArrayList<Wallet>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_wallet, container, false)
        recyclerView=view.findViewById(R.id.wallet_recycler_view)
        progress_bar=view.findViewById(R.id.progress_bar)
        linear_layout=view.findViewById(R.id.linear_layout)

        razorpay=Razorpay(requireActivity(),"rzp_test_lXUzSq2Q1wv9tA")


        progress_bar.visibility=View.VISIBLE
        linear_layout.visibility=View.GONE

        razorpay.getPaymentMethods(object : PaymentMethodsCallback {
            override fun onPaymentMethodsReceived(p0: String?) {
                val walletMap= JSONObject(p0!!).getJSONObject("wallet")
                val itr:Iterator<String> = walletMap.keys()
                while(itr.hasNext()){
                    val key = itr.next()
                    if(walletMap.getBoolean(key)==true){
                        list.add(Wallet(key,walletMap.getBoolean(key)))}
                }
                recyclerView.adapter= WalletAdapter(list,requireActivity(),checkOutViewModel.cartTotal)

                progress_bar.visibility=View.GONE
                linear_layout.visibility=View.VISIBLE
            }

            override fun onError(p0: String?) {
                Toast.makeText(requireContext(),"Bank list not fetched", Toast.LENGTH_SHORT).show()
            }
        })

        return view.rootView
    }

}