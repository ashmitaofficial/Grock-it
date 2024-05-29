package com.example.groceryapp.home.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.favorite.FavoriteAdapter
import com.example.groceryapp.home.favorite.FavoriteViewModel

class CartFragment : Fragment() {

    val cartViewModel: CartViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var constraintLayout: ConstraintLayout
     lateinit var progress_bar:ProgressBar
     lateinit var AddBasket_btn:AppCompatButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerView = view.findViewById(R.id.cart_recycler_view)
        progress_bar=view.findViewById(R.id.progress_bar)
        constraintLayout=view.findViewById(R.id.constraint_layout)
        AddBasket_btn=view.findViewById(R.id.AddBasket_btn)

        constraintLayout.visibility=View.GONE
        progress_bar.visibility=View.VISIBLE
        cartViewModel.getCart(requireContext(),this)

        cartViewModel.livedata.observe(viewLifecycleOwner){
            if(it.productList.size<=0){
                AddBasket_btn.visibility=View.GONE
            }else{
                AddBasket_btn.visibility=View.VISIBLE
            }
            recyclerView.adapter = CartAdapter(it.productList,
                plusClicked = {
                cartViewModel.addCart(requireContext(),it)
            },
               removeCartItem =  {
                    cartViewModel.removeItem(requireContext(),it)
                },
                minusClicked = {
                    cartViewModel.removeCartMinus(requireContext(),it)
                })

            progress_bar.visibility=View.GONE
            constraintLayout.visibility=View.VISIBLE
        }





        return view.rootView
    }

}