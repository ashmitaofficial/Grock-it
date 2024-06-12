package com.example.groceryapp.checkout

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.dao.Response
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.home.shop.ShopAdapter
import com.example.groceryapp.home.shop.ShopFragment
import com.google.android.gms.common.ErrorDialogFragment
import com.google.gson.Gson
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.razorpay.Razorpay
import org.json.JSONObject
import kotlin.math.abs

class PaymentWebViewFragment : Fragment(),PaymentResultWithDataListener {

    lateinit var webView: WebView
    lateinit var razorpay:Razorpay
    val checkOutViewModel: CheckOutViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val payload = JSONObject(this.arguments?.getString(AppConstants.PAYLOAD, ""))

        val view= inflater.inflate(R.layout.fragment_payment_web_view, container, false)
        webView=view.findViewById(R.id.webView)
        razorpay=Razorpay(requireActivity(),"rzp_test_lXUzSq2Q1wv9tA")

        //setting webView to razorpay
        razorpay.setWebView(webView)

        val map= HashMap<String,Any?>()
        map.put("amount",checkOutViewModel.cartTotal)
        map.put("currency","INR")

        checkOutViewModel.createOrder(requireContext(),map)

        checkOutViewModel.orderLiveData.observe(viewLifecycleOwner){
            payload.put("order_id",it)
            razorpay.submit(payload,this )
        }

        checkOutViewModel.orderFailLiveData.observe(viewLifecycleOwner){
         val abc= it

        }

        return view.rootView
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        Toast.makeText(requireContext(),"razorpay_success",Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,OrderAcceptedFragment::class.java,null)
            .commit()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.error_dialog_box)

        dialog.getWindow()?.getAttributes()?.width = WindowManager.LayoutParams.FILL_PARENT;

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
        val tryAgain_btn= dialog.findViewById<AppCompatButton>(R.id.tryAgain_btn)
        val back_home_txt=dialog.findViewById<TextView>(R.id.back_home_txt)
        val errorMessage=dialog.findViewById<TextView>(R.id.error_msg)


        Log.e("TAG", p1.toString())
        val errorObject = JSONObject(p1.toString())
        val errorBody = JSONObject(errorObject["error"].toString())
        val desc = errorBody["description"]
        errorMessage.text= desc.toString()

           tryAgain_btn.setOnClickListener {
            dialog.dismiss()
            requireActivity().finish()
        }

        back_home_txt.setOnClickListener {
            dialog.dismiss()

            val homeIntent= Intent(requireContext(),HomeActivity::class.java)
            homeIntent.putExtra("screen","payment")
            requireActivity().startActivity(homeIntent)
            requireActivity().finish()

        }
    }

}