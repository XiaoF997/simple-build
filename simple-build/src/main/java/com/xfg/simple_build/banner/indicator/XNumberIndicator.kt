package com.xfg.simple_build.banner.indicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.example.simple_build.R
import com.xfg.simple_build.utils.DisplayUtil

class XNumberIndicator: FrameLayout, IXIndicator<FrameLayout> {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }
    companion object{
        private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
    }
    private fun init() {

    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0)return

        val numIndicator = TextView(context)
        numIndicator.setTextColor(Color.WHITE)
        numIndicator.paint.isFakeBoldText = true
        numIndicator.postInvalidate()
        val params = LayoutParams(VMC, VMC)
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.setMargins(0, 0,  DisplayUtil.dp2px(15f, context.resources),  DisplayUtil.dp2px(15f, context.resources))
        numIndicator.text = "1/$count"
        addView(numIndicator, params)
    }

    override fun onPointChange(current: Int, count: Int) {
        val textView: TextView = getChildAt(0) as TextView
        textView.text = "${current + 1}/$count"
    }
}