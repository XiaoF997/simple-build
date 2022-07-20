package com.xfg.simple_build.banner.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.viewpager.widget.PagerAdapter
import com.xfg.simple_build.banner.core.IXBanner
import com.xfg.simple_build.banner.core.XBannerMo
import java.lang.IllegalArgumentException

class XBannerAdapter(private val mContext: Context) : PagerAdapter() {


    var mClickListener: IXBanner.OnBannerClickListener? = null

    var mBindAdapter: IBindAdapter? = null
    set(value) {
        field = value
        initCachedView()
        notifyDataSetChanged()
    }

    var mAutoPlay = true

    var mLoop = true

    var mLayoutResId = -1

    private var mCacheViews: SparseArray<XBannerViewHolder> = SparseArray()

    var models: MutableList<in XBannerMo>? = null

    override fun getCount(): Int {
        return if (mAutoPlay) Int.MAX_VALUE
        else{
            if (mLoop) Int.MAX_VALUE else getRealCount()
        }
    }


    fun getRealCount(): Int{
        return models?.size ?: 0
    }

    fun getFirstItem(): Int{
        return Int.MAX_VALUE / 2 - (Int.MAX_VALUE /2) % getRealCount()
    }

    private fun onBind(viewHolder: XBannerViewHolder, bannerMo: XBannerMo?, position: Int){
        viewHolder.rootView.setOnClickListener{
              if (mClickListener != null){
                  mClickListener?.onBannerClick(viewHolder, bannerMo!!, position)
              }
        }
        if (mBindAdapter != null){
            mBindAdapter?.bind(viewHolder, bannerMo!!, position)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var realPostiton = position
        if (getRealCount() > 0){
            realPostiton = position % getRealCount()
        }
        val viewHolder = mCacheViews[realPostiton]
        if (container == viewHolder.rootView.parent){
            container.removeView(viewHolder.rootView)
        }
        //数据绑定
        onBind(viewHolder, models!![realPostiton] as XBannerMo?, realPostiton)
        if (viewHolder.rootView.parent != null){
            (viewHolder.rootView.parent as ViewGroup).removeView(viewHolder.rootView)
        }
        container.addView(viewHolder.rootView)
        return viewHolder.rootView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    private fun initCachedView(){
        mCacheViews = SparseArray()
        for ((index, _) in models!!.withIndex()){
            val viewHolder: XBannerViewHolder? =
                createView(LayoutInflater.from(mContext), null)?.let { it1 -> XBannerViewHolder(it1) }
            mCacheViews.put(index, viewHolder)
        }
    }

    private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?): View? {
        if (mLayoutResId == -1){
            throw IllegalArgumentException("you must be set setLayoutResId first")
        }
        return layoutInflater.inflate(mLayoutResId, parent, false)
    }


    companion object{
        class XBannerViewHolder(val rootView: View){

            private var viewSparseArray:SparseArray<View>? = null

            fun <T: View> findViewById(id: Int): T?{
                if (rootView !is ViewGroup){
                    return rootView as T
                }
                if (this.viewSparseArray == null){
                    this.viewSparseArray = SparseArray(1)
                }
                var child: T? = null
                viewSparseArray!![id]?.let {
                    child = it as T
                }

                if (child == null){
                    child = rootView.findViewById(id)
                    this.viewSparseArray?.put(id, child)
                }
                return child

            }

        }
    }

}