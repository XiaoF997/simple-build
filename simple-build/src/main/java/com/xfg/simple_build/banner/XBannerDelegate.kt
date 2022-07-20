package com.xfg.simple_build.banner

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.example.simple_build.R
import com.xfg.simple_build.banner.adapter.IBindAdapter
import com.xfg.simple_build.banner.adapter.XBannerAdapter
import com.xfg.simple_build.banner.core.IXBanner
import com.xfg.simple_build.banner.core.XBannerMo
import com.xfg.simple_build.banner.core.XViewPager
import com.xfg.simple_build.banner.indicator.IXIndicator
import com.xfg.simple_build.banner.indicator.XCircleIndicator

class XBannerDelegate(val context: Context, val mBanner: XBanner) : IXBanner,
    ViewPager.OnPageChangeListener {

    private var mAdapter: XBannerAdapter? = null

    private var mXIndicator: IXIndicator<*>? = null

    private var mAutoPlay: Boolean = true

    private var mLoop: Boolean = true

    private lateinit var mXBannerMos: MutableList<in XBannerMo>

    private var mOnPageChangeListener: ViewPager.OnPageChangeListener? = null

    private var mIntervalTime = 5000

    private var mOnBannerClickListener: IXBanner.OnBannerClickListener? = null

    private var mXViewPager: XViewPager? = null

    override fun setBannerData(layoutResId: Int, dataList: MutableList<in XBannerMo>) {
        mXBannerMos = dataList
        init(layoutResId)
    }

    private fun init(layoutResId: Int) {
        if (mAdapter == null){
            mAdapter = XBannerAdapter(context)
        }
        if (mXIndicator == null){
            mXIndicator = XCircleIndicator(context)
        }
        mXIndicator?.onInflate(mXBannerMos.size)
        mAdapter?.let {
            it.mLayoutResId = layoutResId
            it.models = mXBannerMos
            it.mAutoPlay = mAutoPlay
            it.mLoop = mLoop
            it.mClickListener = mOnBannerClickListener
        }

        mXViewPager = XViewPager(context)
        mXViewPager?.mIntervalTime = mIntervalTime
        mXViewPager?.addOnPageChangeListener(this)
        mXViewPager?.mAutoPlay = mAutoPlay
        mXViewPager?.adapter = mAdapter

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        if ((mLoop || mAutoPlay) && mAdapter!!.getRealCount() != 0){
            val firstItem = mAdapter?.getFirstItem()
            firstItem?.let { mXViewPager?.setCurrentItem(it, false) }
        }
        //清除所有的view
        mBanner.removeAllViews()
        mBanner.addView(mXViewPager, params)
        mBanner.addView(mXIndicator!!.get(), params)

    }

    override fun setBannerData(dataList: MutableList<in XBannerMo>) {
        setBannerData(R.layout.x_banner_item_image, dataList)
    }

    override fun setXIndicator(indication: IXIndicator<*>) {
        this.mXIndicator = indication
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        this.mAutoPlay = autoPlay
        mAdapter?.mAutoPlay = autoPlay
        mXViewPager?.mAutoPlay = autoPlay
    }

    override fun setLoop(loop: Boolean) {
        this.mLoop = loop
    }

    override fun setIntervalTime(intervalTime: Int) {
        if (intervalTime > 0){
            this.mIntervalTime = intervalTime
        }
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        this.mAdapter?.mBindAdapter = bindAdapter
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener
    }

    override fun setOnBannerClickListener(onBannerClickListener: IXBanner.OnBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mAdapter?.getRealCount() != 0){
            mOnPageChangeListener?.onPageScrolled(position % mAdapter!!.getRealCount(), positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        var mPosition = position
        if (mAdapter?.getRealCount() == 0){
            return
        }
        mPosition %= mAdapter!!.getRealCount()
        mOnPageChangeListener?.onPageSelected(mPosition)
        mXIndicator?.onPointChange(mPosition, mAdapter!!.getRealCount())
    }


    override fun onPageScrollStateChanged(state: Int) {
        mOnPageChangeListener?.onPageScrollStateChanged(state)
    }
}