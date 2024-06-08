package com.example.groceryapp.checkout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckOutBottomSheet : BottomSheetDialogFragment() {

    lateinit var totalAmout: TextView
    lateinit var proceedBtn: AppCompatButton
    lateinit var payment_method_icon: ImageButton
    lateinit var cgst: TextView
    lateinit var sgst: TextView
    lateinit var close_btn: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout_bottom_sheet, container, false)
        totalAmout = view.findViewById(R.id.totalAmout)
        cgst=view.findViewById(R.id.cgst)
        sgst=view.findViewById(R.id.sgst)
        close_btn=view.findViewById(R.id.close_btn)

        val total = this.arguments?.getString(AppConstants.TOTAL, "")
        val cgstValue = this.arguments?.getString(AppConstants.CGST, "")
        val sgst_value = this.arguments?.getString(AppConstants.SGST, "")

        totalAmout.text = total
        cgst.text=cgstValue
        sgst.text=sgst_value

        dialog?.setCanceledOnTouchOutside(false)

        proceedBtn = view.findViewById(R.id.proceed_btn)
        payment_method_icon = view.findViewById(R.id.payment_method_icon)


        proceedBtn.setOnClickListener{
            dismiss()
            val checkoutIntent= Intent(requireActivity(),CheckoutActivity::class.java)
            checkoutIntent.putExtra("total",total)
            requireActivity().startActivity(checkoutIntent)
        }

        close_btn.setOnClickListener {
            dismiss()
        }

        return view.rootView
    }
}