package com.example.groceryapp.checkout.debit_card

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.base.utils.SharedPreferenceClass
import com.example.groceryapp.base.utils.Validation.isValidEmail
import com.example.groceryapp.checkout.CheckOutViewModel
import com.example.groceryapp.checkout.PaymentWebViewFragment
import org.json.JSONObject

class DebitCardFragment : Fragment() {

    val checkOutViewModel: CheckOutViewModel by activityViewModels()
    lateinit var cardNum_field: EditText
    lateinit var expiry_field: EditText
    lateinit var cvv_field: EditText
    lateinit var cardholder_name_field: EditText
    lateinit var payBtn: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_debit_card, container, false)

        cardNum_field = view.findViewById(R.id.cardNum_editText)
        expiry_field = view.findViewById(R.id.expiry_editText)
        cvv_field = view.findViewById(R.id.cvv_editText)
        cardholder_name_field = view.findViewById(R.id.cardholderName_editText)
        payBtn = view.findViewById(R.id.payBtn)

        payBtn.setOnClickListener {

           var email= SharedPreferenceClass.getEmail(requireContext())
            if(email?.isValidEmail()==false){
             email= "test@gmail.com"
            }

            val payload = JSONObject()
            payload.put("amount", checkOutViewModel.cartTotal)
            payload.put("currency", "INR")
            payload.put("email", email)
            payload.put("card[number]", cardNum_field.text)
            payload.put("card[expiry_month]", expiry_field.text.toString().substring(0, 2).toInt())
            payload.put("card[expiry_year]", expiry_field.text.toString().substring(3).toInt())
            payload.put("card[cvv]", cvv_field.text)
            payload.put("method", "card")
            payload.put("contact", "+917777777777")

            val bundle = Bundle()
            bundle.putString(AppConstants.PAYLOAD, payload.toString())
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaymentWebViewFragment::class.java, bundle)
                .commit()

        }

        expiry_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, removed: Int, added: Int) {
                if (start == 1 && start + added == 2 && p0?.contains('/') == false) {
                    expiry_field.setText(p0.toString() + "/")
                    expiry_field.setSelection(expiry_field.length())
                } else if (start == 3 && start - removed == 2 && p0?.contains('/') == true) {
                    expiry_field.setText(p0.toString().replace("/", ""))
                    expiry_field.setSelection(expiry_field.length())
                }
            }
        })

        return view.rootView
    }

}