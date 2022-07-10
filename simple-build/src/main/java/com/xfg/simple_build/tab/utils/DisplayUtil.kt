package com.xfg.simple_build.tab.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager


class DisplayUtil {

    companion object{

        fun dp2px(dp: Float, resources: Resources): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.displayMetrics
            )
                .toInt()
        }

        fun getDisplayWidthInPx(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (wm != null) {
                val display: Display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                return size.x
            }
            return 0
        }

        fun getDisplayHeightInPx(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (wm != null) {
                val display: Display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                return size.y
            }
            return 0
        }

    }
}