package com.example.groceryapp.banner

import android.view.View
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class BannerViewHolder(itemView: View): BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {

        val data = item.data as ArrayList<Banner>
        val carouselView = itemView.findViewById<CarouselView>(R.id.banner_corousel)

        val imageListener =
            ImageListener { position, imageView ->
//                imageView.scaleType = ImageView.ScaleType.FIT_XY
                Picasso.get().load(data[position].url.toString()).into(imageView)
            }

        carouselView.setPageCount(data.size);
        carouselView.setImageListener(imageListener)
    }
}