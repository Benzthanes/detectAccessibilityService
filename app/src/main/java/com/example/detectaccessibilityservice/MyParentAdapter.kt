package com.example.detectaccessibilityservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.Collections

class MyParentAdapter(private val myDataList: List<MyDataBig>) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TYPE_BIG = 0
        const val TYPE_SMALL = 1
    }

    override fun getItemViewType(position: Int): Int {
        val data = myDataList[position]
        return when (data.type) {
            "small" -> TYPE_SMALL
            "big" -> TYPE_BIG
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == TYPE_SMALL) {
            return SmallViewHolder(inflater.inflate(R.layout.item_layout_small, parent, false))
        } else {
            return BigViewHolder(inflater.inflate(R.layout.item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_BIG -> {
                val bigViewHolder = holder as BigViewHolder
                val data = myDataList[position].myDataList.first()
                bigViewHolder.textView.text = data.text
                bigViewHolder.imageView.setImageResource(data.imageResId)
            }

            TYPE_SMALL -> {
                val smallViewHolder = holder as SmallViewHolder
                val itemAdapter = MyAdapter(myDataList[position].myDataList)
                val layoutManager = GridLayoutManager(holder.itemView.context, 3)
                smallViewHolder.recyclerView.layoutManager = layoutManager
                smallViewHolder.recyclerView.adapter = itemAdapter
                val itemTouchHelper = ItemTouchHelper(DragAndDropCallback(itemAdapter))
                itemTouchHelper.attachToRecyclerView(smallViewHolder.recyclerView)
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(myDataList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}

class SmallViewHolder(itemView: View) : ViewHolder(itemView) {
    val recyclerView: RecyclerView = itemView.findViewById(R.id.nestedRecyclerView)
}

class BigViewHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.item_text_view)
    val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
}


data class MyDataBig(val myDataList: List<MyData>, val type: String)
