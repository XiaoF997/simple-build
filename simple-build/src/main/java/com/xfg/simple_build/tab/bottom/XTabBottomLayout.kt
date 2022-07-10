package com.xfg.simple_build.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.simple_build.R
import com.xfg.simple_build.tab.common.IXTabLayout
import com.xfg.simple_build.tab.utils.DisplayUtil
import com.xfg.simple_build.tab.utils.XViewUtil

class XTabBottomLayout: FrameLayout, IXTabLayout<XTabBottom, XTabBottomInfo<*>> {

    private val tabSelectedChangeListener: MutableList<IXTabLayout.OnTabSelectedListener<XTabBottomInfo<*>>> = mutableListOf()
    private var selectedInfo: XTabBottomInfo<*>? = null
    private var bottomAlpha = 1f
    //tabbottom高度
    private var tabBottomHeight:Float = 50f
    //tabbottom头部线条的高度
    private var bottomLineHeight = 0.5f
    private var bottomLineColor = "#dfe0e1"
    private lateinit var infoList: MutableList<XTabBottomInfo<*>>

    private val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun findTab(data: XTabBottomInfo<*>): XTabBottom? {

        val ll = findViewWithTag<ViewGroup>(TAG_TAB_BOTTOM)
        for (i in ll.children){
            if (i is XTabBottom){
                val tab = i as XTabBottom
                if (tab.tabInfo == data){
                    return tab
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: IXTabLayout.OnTabSelectedListener<XTabBottomInfo<*>>) {
        tabSelectedChangeListener.add(listener)
    }

    override fun defaultSelected(defaultInfo: XTabBottomInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<XTabBottomInfo<*>>) {
        if (infoList.isEmpty()){
            return
        }
        this.infoList = infoList as MutableList<XTabBottomInfo<*>>
        //移除之前已经添加的view
        for (i in childCount - 1 until 0){
            removeViewAt(i)
        }
        selectedInfo = null
        addBackground()
        val ll = FrameLayout(context)
        ll.tag = TAG_TAB_BOTTOM
        val width = DisplayUtil.getDisplayWidthInPx(context )/ infoList.size
        val height = DisplayUtil.dp2px(tabBottomHeight, resources)
        for ((index, value) in infoList.withIndex()){
            val params = LayoutParams(width, height)
            params.gravity = Gravity.BOTTOM
            params.leftMargin = index * width

            val tabBottom = XTabBottom(context)
            tabSelectedChangeListener.add(tabBottom)
            tabBottom.setXTabInfo(value)
            ll.addView(tabBottom, params)
            tabBottom.setOnClickListener{
                onSelected(value)
            }
        }
        val flParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        flParams.gravity = Gravity.BOTTOM

        addBottomLine()
        addView(ll, flParams)
        fixContentView()
    }

    private fun addBottomLine(){
        val bottomLine = View(context)
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor))
        val bottomLineParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(bottomLineHeight, resources))
        bottomLineParams.gravity = Gravity.BOTTOM
        bottomLineParams.bottomMargin = DisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, resources)
        addView(bottomLine, bottomLineParams)
        bottomLine.alpha = bottomAlpha
    }

    private fun onSelected(nextInfo: XTabBottomInfo<*>){
        for (i in tabSelectedChangeListener){
            i.onTabSelectedListener(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
        }
        this.selectedInfo = nextInfo
    }

    private fun addBackground(){
        val view = LayoutInflater.from(context).inflate(R.layout.x_bottom_layout_bg, null)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(tabBottomHeight, resources))
        params.gravity = Gravity.BOTTOM
        addView(view, params)
        view.alpha = bottomAlpha
    }

    fun setTabAlpha(alpha: Float){
        this.bottomAlpha = alpha
    }

    fun setTabHeight(tabBottomHeight: Float){
        this.tabBottomHeight = tabBottomHeight
    }
    fun setBottomLineHeight(bottomLineHeight: Float){
        this.bottomLineHeight = bottomLineHeight
    }

    fun setBottomLineColor(bottomLineColor: String){
        this.bottomLineColor = bottomLineColor
    }

    private fun fixContentView(){
        if ((getChildAt(0)) !is ViewGroup){
            return
        }
        val rootView = getChildAt(0) as ViewGroup
        var targetView:ViewGroup? = XViewUtil.findTypeView(rootView, RecyclerView::class.java)
        if (targetView == null){
            targetView = XViewUtil.findTypeView(rootView, ScrollView::class.java)
        }
        if (targetView == null){
            targetView = XViewUtil.findTypeView(rootView, NestedScrollView::class.java)
        }
        if (targetView == null){
            targetView = XViewUtil.findTypeView(rootView, AbsListView::class.java)
        }
        if (targetView != null){
            targetView.setPadding(0, 0, 0, DisplayUtil.dp2px(tabBottomHeight, resources))
            targetView.clipToPadding = false
        }
    }
}