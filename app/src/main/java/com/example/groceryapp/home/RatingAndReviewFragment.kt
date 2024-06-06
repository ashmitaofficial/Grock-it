package com.example.groceryapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.groceryapp.R
import com.google.android.material.appbar.MaterialToolbar


class RatingAndReviewFragment : Fragment() {

    lateinit var ratingBar:RatingBar
    lateinit var review_container:EditText
    lateinit var review_btn:AppCompatButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_rating_and_review, container, false)

        ratingBar=view.findViewById(R.id.ratingBar)
        review_container=view.findViewById(R.id.review_container)
        review_btn=view.findViewById(R.id.review_btn)

        review_btn.setOnClickListener{
            Toast.makeText(requireContext(),"You rated:"+ratingBar.rating,Toast.LENGTH_LONG).show()
        }


        return view.rootView
    }

}