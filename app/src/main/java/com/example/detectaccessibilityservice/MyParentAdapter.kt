package com.example.detectaccessibilityservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MyParentAdapter(private val nestedDataList: MyDataBig) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TYPE_BIG = 0
        const val TYPE_SMALL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_SMALL
            1 -> TYPE_BIG
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == TYPE_SMALL) {
            return SmallViewHolder(inflater.inflate(R.layout.item_layout_big, parent, false))
        } else {
            return BigViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_BIG -> {
                val bigViewHolder = holder as BigViewHolder
                bigViewHolder.textView.text = nestedDataList.text
                bigViewHolder.imageView.setImageResource(nestedDataList.imageResId)
            }

            TYPE_SMALL -> {
                val smallViewHolder = holder as SmallViewHolder
                val nestedAdapter = MyAdapter(nestedDataList.myDataList)
                val layoutManager = GridLayoutManager(holder.itemView.context, 3)
                smallViewHolder.recyclerView.layoutManager = layoutManager
                smallViewHolder.recyclerView.adapter = nestedAdapter
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}

class SmallViewHolder(itemView: View) : ViewHolder(itemView) {
    val recyclerView: RecyclerView = itemView.findViewById(R.id.nestedRecyclerView)
}

class BigViewHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.item_text_view)
    val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
}

data class MyDataBig(val text: String, val imageResId: Int, val myDataList: List<MyData>)