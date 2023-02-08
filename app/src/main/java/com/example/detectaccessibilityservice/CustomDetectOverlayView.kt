package com.example.detectaccessibilityservice

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class CustomDetectOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onFilterTouchEventForSecurity(event: MotionEvent): Boolean {
        val flag_FLAG_WINDOW_IS_OBSCURED =
            (event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_OBSCURED
        val flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED =
            (event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED) == MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED
        Log.e("FLAG_WINDOW_IS_OBSCURED", flag_FLAG_WINDOW_IS_OBSCURED.toString())
        Log.e(
            "FLAG_WINDOW_IS_PARTIALLY_OBSCURED",
            flag_FLAG_WINDOW_IS_PARTIALLY_OBSCURED.toString()
        )
        return if ((event.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED)
            == MotionEvent.FLAG_WINDOW_IS_OBSCURED
            || (event.flags and MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED)
            == MotionEvent.FLAG_WINDOW_IS_PARTIALLY_OBSCURED
        ) {
            /// do something when detected
            false
        } else super.onFilterTouchEventForSecurity(event)
    }

}