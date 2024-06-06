package com.example.groceryapp.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R

class PaymentMethodFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val checkOutViewModel: CheckOutViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.payment_option_recycler_view, container, false)
        recyclerView = view.findViewById(R.id.payment_recycler_view)

        checkOutViewModel.getPaymentMethod(requireContext())
        checkOutViewModel.liveData.observe(viewLifecycleOwner){
            recyclerView.adapter= PaymentMethodAdapter(it,this)
        }




        return view.rootView
    }

}