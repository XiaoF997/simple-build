package com.xfg.simple_build.banner.indicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.example.simple_build.R
import com.xfg.simple_build.utils.DisplayUtil

class XCircleIndicator: FrameLayout, IXIndicator<FrameLayout> {


    private var mPointTopBottomPadding  = 0
    private var mPointLeftRightPadding = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    private fun init() {
        mPointLeftRightPadding = DisplayUtil.dp2px(5f, context.resources)
        mPointTopBottomPadding = DisplayUtil.dp2px(15f, context.resources)
    }

    companion object{
        private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT

        @DrawableRes
        private val mPointNormal = R.drawable.shape_point_normal
        @DrawableRes
        private val mPointSelected = R.drawable.shape_point_select
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0){
            return
        }
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL

        var imageView: ImageView
        val params = LinearLayout.LayoutParams(VMC, VMC)
        params.gravity = Gravity.CENTER_VERTICAL
        params.setMargins(mPointLeftRightPadding,mPointTopBottomPadding,  mPointLeftRightPadding, mPointTopBottomPadding)

        for (i in 0 until count){
            imageView = ImageView(context)
            imageView.layoutParams = params
            if (i == 0){
                imageView.setImageResource(mPointSelected)
            }else{
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }

        val layoutParams = LayoutParams(VMC, VMC)
        layoutParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, layoutParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup: ViewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount){
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current){
                imageView.setImageResource(mPointSelected)
            }else{
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }

    }
}