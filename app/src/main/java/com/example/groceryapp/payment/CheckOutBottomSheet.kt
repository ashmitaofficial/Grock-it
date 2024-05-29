package com.example.groceryapp.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.groceryapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckOutBottomSheet: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_checkout_bottom_sheet, container, false)

        return view?.rootView
    }
}