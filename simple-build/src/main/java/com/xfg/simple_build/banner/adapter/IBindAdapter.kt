package com.xfg.simple_build.banner.adapter

import com.xfg.simple_build.banner.core.XBannerMo

/**
 * Banner的数据绑定接口，基于该接口实现数据的绑定
 */

interface IBindAdapter {
    fun bind(viewHolder: XBannerAdapter.Companion.XBannerViewHolder, mo: XBannerMo, position: Int)
}