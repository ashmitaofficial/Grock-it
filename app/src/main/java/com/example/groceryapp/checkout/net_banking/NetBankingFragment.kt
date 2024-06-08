package com.example.groceryapp.checkout.net_banking

import android.os.Bundle
import android.util.Log
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
import com.razorpay.PaymentMethodsCallback
import com.razorpay.Razorpay
import org.json.JSONObject

class NetBankingFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val checkOutViewModel: CheckOutViewModel by activityViewModels()
    lateinit var razorpay: Razorpay
    lateinit var progress_bar: ProgressBar
    lateinit var linear_layout: LinearLayout
     var list = ArrayList<Bank>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.net_banking_recycler_view, container, false)
        recyclerView=view.findViewById(R.id.bank_name_recycler_view)
        progress_bar=view.findViewById(R.id.progress_bar)
        linear_layout=view.findViewById(R.id.linear_layout)

        progress_bar.visibility=View.VISIBLE
        linear_layout.visibility=View.GONE



        razorpay=Razorpay(requireActivity(),"rzp_test_lXUzSq2Q1wv9tA")

        razorpay.getPaymentMethods(object :PaymentMethodsCallback{
            override fun onPaymentMethodsReceived(p0: String?) {
                val bankMap= JSONObject(p0!!).getJSONObject("netbanking")
                val itr:Iterator<String> = bankMap.keys()
                while(itr.hasNext()){
                    val key = itr.next()
                    list.add(Bank(key,bankMap.getString(key)))
                }
                recyclerView.adapter= NetBankingAdapter(list,requireActivity(),checkOutViewModel.cartTotal)
                progress_bar.visibility=View.GONE
                linear_layout.visibility=View.VISIBLE
            }

            override fun onError(p0: String?) {
               Toast.makeText(requireContext(),"Bank list not fetched",Toast.LENGTH_SHORT).show()
            }
        })

        return view.rootView
    }

}