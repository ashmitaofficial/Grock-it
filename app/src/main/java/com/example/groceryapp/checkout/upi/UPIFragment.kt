package com.example.groceryapp.checkout.upi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.checkout.CheckOutViewModel
import com.example.groceryapp.checkout.PaymentWebViewFragment
import org.json.JSONObject

class UPIFragment : Fragment() {

    val checkOutViewModel: CheckOutViewModel by activityViewModels()
    lateinit var cardNum:EditText
    lateinit var payBtn:AppCompatButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_u_p_i, container, false)
        cardNum=view.findViewById(R.id.cardNum_field)
        payBtn=view.findViewById(R.id.payBtn)

        payBtn.setOnClickListener {
            val payload= JSONObject()
            payload.put("amount",checkOutViewModel.cartTotal)
            payload.put("currency","INR")
            payload.put("email", SharedPreferenceClass.getEmail(requireContext()))
            payload.put("contact","+917777777777")
            payload.put("method","upi")
            payload.put("vpa",cardNum.text)

            val bundle=Bundle()
            bundle.putString("payload",payload.toString())
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaymentWebViewFragment::class.java,bundle)
                .commit()

        }




        return view.rootView
    }

}