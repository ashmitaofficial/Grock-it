package com.example.groceryapp.home.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.banner.BannerViewHolder
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.category.CategoryAdapter
import com.example.groceryapp.category.CategoryViewHolder
import com.example.groceryapp.home.TempViewHolder
import com.example.groceryapp.bestSelling.BestSellerViewHolder
import com.example.groceryapp.recommend.RecommendedViewHolder
import com.example.groceryapp.label.LabelViewHolder
import com.example.groceryapp.location.LocationViewHolder
import com.example.groceryapp.offer.OfferViewHolder
import com.example.groceryapp.product.ProductAdapter
import com.example.groceryapp.search.SearchViewHolder

class ShopAdapter(private val list: ArrayList<DynamicItem>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    companion object {
        const val TYPE_BANNER = 0
        const val TYPE_LABEL = 1
        const val TYPE_OFFER = 2
        const val TYPE_BEST_SELLER = 3
        const val TYPE_CATEGORIES = 4
        const val TYPE_RECOMMEND = 5
        const val TYPE_SEARCH = 6
        const val TYPE_LOCATION = 7

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            TYPE_BANNER -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
                return BannerViewHolder(view)
            }

            TYPE_LABEL -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.label, parent, false)
                return LabelViewHolder(view)
            }

            TYPE_OFFER -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.home_offer_item, parent, false)
                return OfferViewHolder(view)
            }

            TYPE_BEST_SELLER -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.home_best_seller_item, parent, false)
                return BestSellerViewHolder(view)
            }


            TYPE_CATEGORIES -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.home_grocery_item, parent, false)
                return CategoryViewHolder(view)
            }

            TYPE_RECOMMEND -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recommended_item, parent, false)
                return RecommendedViewHolder(view)
            }

            TYPE_SEARCH -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
                return SearchViewHolder(view)
            }
            TYPE_LOCATION -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
                return LocationViewHolder(view)
            }

        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return TempViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val dyItem = list[position]
        if (dyItem.id == "TYPE_BANNER") {
            return TYPE_BANNER
        } else if (dyItem.id == "TYPE_LABEL") {
            return TYPE_LABEL
        } else if (dyItem.id == "TYPE_OFFER") {
            return TYPE_OFFER
        } else if (dyItem.id == "TYPE_BEST_SELLER") {
            return TYPE_BEST_SELLER
        } else if (dyItem.id == "TYPE_CATEGORIES") {
            return TYPE_CATEGORIES
        } else if (dyItem.id == "TYPE_RECOMMEND") {
            return TYPE_RECOMMEND
        } else if (dyItem.id == "TYPE_SEARCH") {
            return TYPE_SEARCH
        } else if (dyItem.id == "TYPE_LOCATION") {
            return TYPE_LOCATION
        }
        return -1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position])
    }
}