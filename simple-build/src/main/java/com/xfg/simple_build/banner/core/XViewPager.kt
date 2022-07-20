package com.xfg.simple_build.banner.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.xfg.simple_build.banner.adapter.XBannerAdapter
import java.lang.Exception

/**
 * 实现自动翻页的viewPager
 */
class XViewPager : ViewPager {

    var mIntervalTime: Int = 3000

    /**
     * 是否自动播放
     */
    var mAutoPlay: Boolean = true
        set(value) {
            field = value
            if (!field) {
                mHandler.removeCallbacks(mRunnable)
            }else{
                start()
            }
        }

    private var isLayout: Boolean = false

    private val mHandler = Handler()

    private val mRunnable = object : Runnable {
        override fun run() {
            next()
            mHandler.postDelayed(this, mIntervalTime.toLong())
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    fun start() {
        mHandler.removeCallbacksAndMessages(null)
        if (mAutoPlay){
            mHandler.postDelayed(mRunnable, mIntervalTime.toLong())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                start()
            }
            else->{
                stop()
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        isLayout = true
    }

    override fun onDetachedFromWindow() {
        //用于解决viewpager和recycleview混用导致的一些bug
        if((context as Activity).isFinishing)
        super.onDetachedFromWindow()

        stop()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isLayout && adapter != null && adapter!!.count > 0){
            try {
                //用于解决viewpager和recycleview混用导致的一些bug
                val viewPagerClass = ViewPager::class.java
                val mFirstLayoutField = viewPagerClass.getDeclaredField("mFirstLayout")
                mFirstLayoutField.isAccessible = true
                mFirstLayoutField.set(this, false)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        start()
    }

    /**
     * 设置下一个显示的item
     */
    private fun next(): Int {
        var nextPosition = -1
        adapter?.let { adapter ->
            if (adapter.count <= 1) {
                stop()
                return nextPosition
            }
            nextPosition = currentItem + 1
            if (nextPosition >= adapter.count) {
                //下一个索引大于view的最大数量是重新开始
//                nextPosition = adapter.count
                nextPosition = (adapter as XBannerAdapter).getFirstItem()

            }
            setCurrentItem(nextPosition, true)
        }
        stop()
        return nextPosition

    }

}