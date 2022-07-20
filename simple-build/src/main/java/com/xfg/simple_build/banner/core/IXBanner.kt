package com.xfg.simple_build.banner.core

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.xfg.simple_build.banner.adapter.IBindAdapter
import com.xfg.simple_build.banner.adapter.XBannerAdapter
import com.xfg.simple_build.banner.indicator.IXIndicator

interface IXBanner {

    fun setBannerData(@LayoutRes layoutResId: Int, dataList: MutableList<in XBannerMo>)

    fun setBannerData(dataList: MutableList<in XBannerMo>)

    fun setXIndicator(indication: IXIndicator<*>)

    fun setAutoPlay(autoPlay: Boolean)

    fun setLoop(loop: Boolean)

    fun setIntervalTime(intervalTime: Int)

    fun setBindAdapter(bindAdapter: IBindAdapter)

    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener)

    fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)


    interface OnBannerClickListener {
        fun onBannerClick(viewHolder: XBannerAdapter.Companion.XBannerViewHolder, bannerMo: XBannerMo, position: Int)
    }
}