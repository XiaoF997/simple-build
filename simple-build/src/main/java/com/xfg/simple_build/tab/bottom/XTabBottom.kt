package com.xfg.simple_build.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.example.simple_build.R
import com.xfg.simple_build.tab.common.IXTab

class XTabBottom: RelativeLayout, IXTab<XTabBottomInfo<*>> {

    var tabInfo: XTabBottomInfo<*>? = null
        private set
    private lateinit var tabImageView: ImageView
    private lateinit var tabIconView: TextView
    private lateinit var tabNameView: TextView

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
        LayoutInflater.from(context).inflate(R.layout.x_tab_bottom, this)
        tabImageView = findViewById(R.id.iv_image)
        tabIconView = findViewById(R.id.tv_icon)
        tabNameView = findViewById(R.id.tv_name)

    }

    override fun setXTabInfo(data: XTabBottomInfo<*>) {
        this.tabInfo = data
        inflateInfo(false, true)
    }

    private fun inflateInfo(selected:Boolean, init: Boolean){
        if (tabInfo?.tabType == XTabBottomInfo.TabType.ICON){
            if (init){
                tabImageView.visibility = GONE
                tabIconView.visibility = VISIBLE
                val typeFace = Typeface.createFromAsset(context.assets, tabInfo?.iconFont)
                tabIconView.typeface = typeFace
                tabNameView.text = tabInfo?.name
            }
            if (selected){
                tabIconView.text = if (TextUtils.isEmpty(tabInfo?.selectedIconName)) tabInfo?.defaultIconName else tabInfo?.selectedIconName
                tabIconView.setTextColor(getTextColor(tabInfo?.tintColor))
                tabNameView.setTextColor(getTextColor(tabInfo?.tintColor))
            }else{
                tabIconView.text = tabInfo?.defaultIconName
                tabIconView.setTextColor(getTextColor(tabInfo?.defaultColor))
                tabNameView.setTextColor(getTextColor(tabInfo?.defaultColor))
            }
        }else{
            if (init){
                tabImageView.visibility = VISIBLE
                tabIconView.visibility = GONE
                tabNameView.text = tabInfo?.name
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
        prevInfo: XTabBottomInfo<*>?,
        nextInfo: XTabBottomInfo<*>
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