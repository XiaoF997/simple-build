package com.xfg.simple_build.tab.common

import android.view.ViewGroup

interface IXTabLayout<Tab: ViewGroup, D> {
    fun findTab(data: D): Tab?

    fun addTabSelectedChangeListener(listener: OnTabSelectedListener<D> )

    fun defaultSelected(defaultInfo: D)

    fun inflateInfo(infoList: List<D>)


    interface OnTabSelectedListener<D> {
        fun onTabSelectedListener(index: Int, prevInfo: D?, nextInfo: D)
    }
}