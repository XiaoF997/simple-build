package com.xfg.simple_build.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.example.simple_build.R
import com.xfg.simple_build.banner.adapter.IBindAdapter
import com.xfg.simple_build.banner.core.IXBanner
import com.xfg.simple_build.banner.core.XBannerMo
import com.xfg.simple_build.banner.indicator.IXIndicator

class XBanner : FrameLayout, IXBanner {
    private var delegate: XBannerDelegate = XBannerDelegate(context, this)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initCustomAttrs(context, attrs)
    }

    private fun initCustomAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBanner)
        val autoPlay = typedArray.getBoolean(R.styleable.XBanner_autoPlay, true)
        val loop = typedArray.getBoolean(R.styleable.XBanner_loop, true)
        val intervalTime = typedArray.getInteger(R.styleable.XBanner_intervalTime, -1)
        setAutoPlay(autoPlay)
        setLoop(loop)
        setIntervalTime(intervalTime)
        typedArray.recycle()
    }

    override fun setBannerData(layoutResId: Int, dataList: MutableList<in XBannerMo>) {
        delegate.setBannerData(layoutResId, dataList)
    }

    override fun setBannerData(dataList: MutableList<in XBannerMo>) {
        delegate.setBannerData(dataList)
    }

    override fun setXIndicator(indication: IXIndicator<*>) {
        delegate.setXIndicator(indication)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        delegate.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        delegate.setLoop(loop)
    }

    override fun setIntervalTime(intervalTime: Int) {
        delegate.setIntervalTime(intervalTime)
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        delegate.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        delegate.setOnPageChangeListener(onPageChangeListener)
    }

    override fun setOnBannerClickListener(onBannerClickListener: IXBanner.OnBannerClickListener) {
        delegate.setOnBannerClickListener(onBannerClickListener)
    }
}