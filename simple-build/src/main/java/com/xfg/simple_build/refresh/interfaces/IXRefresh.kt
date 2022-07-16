package com.xfg.simple_build.refresh.interfaces

import com.xfg.simple_build.refresh.XOverView

interface IXRefresh{
    /**
     * 刷新时是否禁止滚动
     */
    fun setDisableRefreshScroll(disableRefreshScroll: Boolean)

    /**
     * 刷新完成
     */
    fun refreshFinished()

    /**
     * 设置下拉刷新监听器
     */
    fun setRefreshListener(listener: XRefreshListener)


    fun setRefreshOverView(xOverView: XOverView)

    interface  XRefreshListener{
        fun onRefresh()

        fun enableRefresh(): Boolean
    }

}