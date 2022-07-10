package com.xfg.simple_build.tab.top

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.children
import com.xfg.simple_build.tab.common.IXTabLayout
import com.xfg.simple_build.tab.utils.DisplayUtil
import kotlin.math.abs

class XTabTopLayout: HorizontalScrollView, IXTabLayout<XTabTop, XTabTopInfo<*>> {
    private val tabSelectedChangeListener: MutableList<IXTabLayout.OnTabSelectedListener<XTabTopInfo<*>>> = mutableListOf()
    private var selectedInfo: XTabTopInfo<*>? = null
    private lateinit var infoList: MutableList<XTabTopInfo<*>>

    private val TAG_TAB_BOTTOM = "TAG_TAB_TOP"


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        isVerticalScrollBarEnabled = false
    }

    override fun findTab(data: XTabTopInfo<*>): XTabTop? {
        val ll = getRootLayout(false)
        for (i in ll.children){
            if (i is XTabTop){
                val tab = i as XTabTop
                if (tab.tabInfo == data){
                    return tab
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: IXTabLayout.OnTabSelectedListener<XTabTopInfo<*>>) {
        tabSelectedChangeListener.add(listener)
    }

    override fun defaultSelected(defaultInfo: XTabTopInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<XTabTopInfo<*>>) {
        if (infoList.isEmpty()){
            return
        }
        this.infoList = infoList as MutableList<XTabTopInfo<*>>
        val linearLayout = getRootLayout(true)
        //移除之前已经添加的view
        val iterable = tabSelectedChangeListener.iterator()
        while (iterable.hasNext()){
            if (iterable.next() is XTabTop){
                iterable.remove()
            }
        }
//        for (i in linearLayout.childCount - 1 until 0){
//            removeViewAt(i)
//        }
        selectedInfo = null
        for ((index, value) in infoList.withIndex()){

            val tabTop = XTabTop(context)
            tabSelectedChangeListener.add(tabTop)
            tabTop.setXTabInfo(value)
            linearLayout.addView(tabTop)
            tabTop.setOnClickListener{
                onSelected(value)
            }
        }
    }

    private fun onSelected(nextInfo: XTabTopInfo<*>){
        for (i in tabSelectedChangeListener){
            i.onTabSelectedListener(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
        }
        this.selectedInfo = nextInfo
        autoScroll(nextInfo)
    }

    private fun autoScroll(nextInfo: XTabTopInfo<*>) {
        val tabTop = findTab(nextInfo) ?: return
        val index = infoList.indexOf(nextInfo)
        val loc:IntArray = intArrayOf(0,0)
        tabTop.getLocationInWindow(loc)
        var scrollWidth: Int = 0
        if (tabWidth == 0){
            tabWidth = tabTop.width
        }
        //判断点击了左边还是右边
        scrollWidth = if ((loc[0] + tabWidth/2) >( DisplayUtil.getDisplayWidthInPx(context) / 2)){
            rangeScrollWidth(index, 2)
        }else{

            rangeScrollWidth(index, -2)
        }
        scrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动范围
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth: Int = 0
        for (i in 0 until abs(range)){
            var next = 0
            next = if (range < 0){
                range + i + index
            }else{
                range - i + index
            }
            if (next >= 0 && next < infoList.size){
                if (range < 0){
                    scrollWidth-=scrollWidth(next, false)
                }else{
                    scrollWidth+=scrollWidth(next, true)
                }
            }
        }
        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     */
    private fun scrollWidth(index: Int, toRight: Boolean): Int{
        val target = findTab(infoList[index]) ?: return 0
        val rect = Rect()
        target.getLocalVisibleRect(rect)
        if (toRight){
            return if (rect.right > tabWidth){
                tabWidth
            }else{
                tabWidth - rect.right
            }
        }else{
            if (rect.left <= -tabWidth){
                return tabWidth
            }else if (rect.left > 0){
                return rect.left
            }
            return 0
        }
    }

    var tabWidth: Int = 0

    private fun getRootLayout(clear: Boolean): LinearLayout{
        var rootView: LinearLayout? = null
        getChildAt(0)?.let {
            rootView = it  as LinearLayout
        }
        if (rootView == null){
            rootView = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
            }
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            addView(rootView, params)
        }else if (clear){
            rootView?.removeAllViews()
        }
        return rootView as LinearLayout
    }
}