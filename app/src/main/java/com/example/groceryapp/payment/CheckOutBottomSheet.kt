package com.example.groceryapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.groceryapp.R
import com.example.groceryapp.checkout.PaymentMethodFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckOutBottomSheet : BottomSheetDialogFragment() {


    lateinit var totalAmout: TextView
    lateinit var placeOrder_btn: AppCompatButton
    lateinit var payment_method_icon: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout_bottom_sheet, container, false)
        totalAmout = view.findViewById(R.id.totalAmout)

        val total = this.arguments?.getString("key", "")

        totalAmout.text = total
        placeOrder_btn = view.findViewById(R.id.placeOrder_btn)
        payment_method_icon = view.findViewById(R.id.payment_method_icon)


        payment_method_icon.setOnClickListener {
            dismiss()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaymentMethodFragment::class.java, null)
//                .addToBackStack(null)
                .commit()
        }
        return view.rootView
    }
}