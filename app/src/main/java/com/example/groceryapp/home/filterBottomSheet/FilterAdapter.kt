package com.example.groceryapp.home.filterBottomSheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R

class FilterAdapter(private val list: ArrayList<String>,val context: FragmentActivity): RecyclerView.Adapter<FilterAdapter.MyViewholder>() {

    companion object {
        val selectedItems = ArrayList<String>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.productName.text= list[position]
        holder.productName.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    selectedItems.add(holder.productName.text.toString())
                } else {
                    selectedItems.remove(holder.productName.text.toString())
                }
            }

        })

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view){
        var productName = view.findViewById<CheckBox>(R.id.product_name)
    }

}