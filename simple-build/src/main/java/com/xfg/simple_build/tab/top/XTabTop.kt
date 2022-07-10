package com.xfg.simple_build.tab.top

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.example.simple_build.R
import com.xfg.simple_build.tab.common.IXTab

class XTabTop : RelativeLayout, IXTab<XTabTopInfo<*>> {

    var tabInfo: XTabTopInfo<*>? = null
        private set
    private lateinit var tabImageView: ImageView
    private lateinit var tabNameView: TextView
    private lateinit var indicator: View

    constructor(context: Context?):this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }


    private fun init(){
        LayoutInflater.from(context).inflate(R.layout.x_tab_top, this)
        tabImageView = findViewById(R.id.iv_image)
        tabNameView = findViewById(R.id.tv_name)
        indicator = findViewById(R.id.tab_top_indicator)

    }

    override fun setXTabInfo(data: XTabTopInfo<*>) {
        this.tabInfo = data
        inflateInfo(false, true)
    }

    private fun inflateInfo(selected:Boolean, init: Boolean){
        if (tabInfo?.tabType == XTabTopInfo.TabType.TEXT){
            if (init){
                tabImageView.visibility = GONE
                tabNameView.visibility = VISIBLE
                tabNameView.text = tabInfo?.name
            }
            if (selected){
                indicator.visibility = VISIBLE
                tabNameView.setTextColor(getTextColor(tabInfo?.tintColor))
            }else{
                indicator.visibility = GONE
                tabNameView.setTextColor(getTextColor(tabInfo?.defaultColor))
            }
        }else{
            if (init){
                tabImageView.visibility = VISIBLE
                tabNameView.visibility = GONE
            }

            if (selected){
                tabImageView.setImageBitmap(tabInfo?.selectedBitmap)
            }else{
                tabImageView.setImageBitmap(tabInfo?.defaultBitmap)
            }
        }
    }

    override fun resetHeight(height: Int) {
        val params = layoutParams
        params.height = height
        layoutParams = params
        tabNameView.visibility = GONE
    }

    override fun onTabSelectedListener(
        index: Int,
        prevInfo: XTabTopInfo<*>?,
        nextInfo: XTabTopInfo<*>
    ) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo){
            return
        }
        if (prevInfo == tabInfo){
            inflateInfo(selected = false, init = false)
        }else{
            inflateInfo(selected = true, init = false)
        }
    }

    @ColorInt
    private fun getTextColor(color: Any?): Int{
        return if (color is String){
            Color.parseColor(color)
        }else{
            color as Int
        }
    }
}