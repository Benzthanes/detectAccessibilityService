package com.example.detectaccessibilityservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        // ตัวอย่างข้อมูล
        val dataList = listOf(
            MyData("เติม", R.drawable.ic_launcher_foreground),
            MyData("ถอน", R.drawable.ic_launcher_foreground),
            MyData("โอน", R.drawable.ic_launcher_foreground),
            MyData("สโตร์", R.drawable.ic_launcher_foreground),
            MyData("บัญชี", R.drawable.ic_launcher_foreground),
            MyData("สแกน", R.drawable.ic_launcher_foreground)
        )

        // ค้นหา RecyclerView จาก layout XML
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // สร้าง Adapter ด้วยข้อมูล
        val adapter = MyAdapter(dataList)

        // ตั้งค่า LayoutManager เป็น GridLayoutManager
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 5) 3 else 1
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}