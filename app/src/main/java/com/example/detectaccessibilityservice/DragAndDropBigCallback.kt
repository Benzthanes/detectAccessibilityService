package com.example.detectaccessibilityservice

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragAndDropBigCallback(
    private val adapter: MyParentAdapter
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        adapter.moveItem(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // ไม่ต้องทำอะไรเมื่อ swiped
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder is BigViewHolder) {
            viewHolder.itemView.animate()?.scaleX(1.1f)?.scaleY(1.1f)?.duration = 300
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.animate().scaleX(1f).scaleY(1f).duration = 300
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val clippedDx  = clip(recyclerView.width, viewHolder.itemView.left, viewHolder.itemView.right, dX)
        val clippedDy  = clip(recyclerView.height, viewHolder.itemView.top, viewHolder.itemView.bottom, dY)
        super.onChildDraw(c, recyclerView, viewHolder, clippedDx, clippedDy, actionState, isCurrentlyActive)
    }

    private fun clip(size: Int, start: Int, end: Int, delta: Float): Float {
        val newStart = start + delta
        val newEnd = end + delta

        val oobStart = 0 - newStart
        val oobEnd = newEnd - size

        return when {
            oobStart > 0 -> delta + oobStart
            oobEnd > 0 -> delta - oobEnd
            else -> delta
        }
    }
}