package com.example.detectaccessibilityservice

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {

    companion object {
        // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F4BB] (PERSONAL COMPUTER)
        private const val WOMAN_TECHNOLOGIST = "\uD83D\uDC69\u200D\uD83D\uDCBB"

        // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F3A4] (MICROPHONE)
        private const val WOMAN_SINGER = "\uD83D\uDC69\u200D\uD83C\uDFA4"


        const val EMOJI = "$WOMAN_TECHNOLOGIST $WOMAN_SINGER"

        const val smileEmoji = "\\u{1F60A}"  // 😊

        const val fireEmoji = "{\"icon\": \"\uD83C\uDFA3\"} "   // 🔥

        const val heartEmoji = "\uD83D\uDE0A" // ❤️ (heart needs variation selector)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val emojiTextView: TextView = findViewById(R.id.emoji_text_view)
        emojiTextView.text = "test = $EMOJI $EMOJI $EMOJI $EMOJI $smileEmoji $fireEmoji $heartEmoji"
"{\n" +
    "  \"headerTh\": \"\uD83D\uDC4B สวัสดี\",\n" +
    "  \"headerEn\": \"\uD83C\uDF1E Hello\",\n" +
    "  \"descriptionTh\": \"\uD83C\uDF89 ยินดีต้อนรับ\",\n" +
    "  \"descriptionEn\": \"\uD83C\uDF08 Welcome!\"\n" +
    "}"

        val image: ImageView = findViewById(R.id.imageView)
        val root: View = findViewById(R.id.main)
        val overlay: View = findViewById(R.id.constraintLayout2_overlay)
        root.setOnClickListener {
            overlay.visibility = View.VISIBLE
        }
        overlay.setOnClickListener {
            overlay.visibility = View.GONE
        }
        image.setOnClickListener {

        }

        // ตัวอย่างข้อมูล

        val dataListSmall = MyDataBig(
            listOf(
                MyData("เติม", R.drawable.ic_launcher_foreground),
                MyData("ถอน", R.drawable.ic_launcher_foreground),
                MyData("โอน", R.drawable.ic_launcher_foreground),
                MyData("xxx", R.drawable.ic_launcher_foreground),
                MyData("สโตร์", R.drawable.ic_launcher_foreground),
                MyData("บัญชี", R.drawable.ic_launcher_foreground),
            ), "small"
        )

        val dataListBig =
            MyDataBig(
                listOf(MyData("สแกน", R.drawable.ic_launcher_foreground)),
                "big"
            )
        val dataList = listOf(dataListSmall, dataListBig)

        // ค้นหา RecyclerView จาก layout XML
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // สร้าง Adapter ด้วยข้อมูล
        val adapter = MyParentAdapter(dataList)

        // ตั้งค่า LayoutManager เป็น GridLayoutManager
        val layoutManager = GridLayoutManager(this, 5)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.getItemViewType(position) == MyParentAdapter.TYPE_BIG) {
                    2
                } else {
                    3
                }
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(DragAndDropBigCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}