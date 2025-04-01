package com.example.detectaccessibilityservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.item_text_view)
    val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
}

// ขั้นตอนที่ 2: สร้าง Adapter
class MyAdapter(private val dataList: List<MyData>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // สร้าง View สำหรับไอเท็ม
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // ผูกข้อมูลเข้ากับ View
        val data = dataList[position]
        holder.textView.text = data.text
        holder.imageView.setImageResource(data.imageResId)
    }

    override fun getItemCount(): Int {
        // จำนวนไอเท็มทั้งหมด
        return dataList.size
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(dataList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

}

// ชั้นข้อมูลที่คุณต้องการแสดง
data class MyData(val text: String, val imageResId: Int)