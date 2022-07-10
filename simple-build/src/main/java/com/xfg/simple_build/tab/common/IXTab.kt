package com.xfg.simple_build.tab.common

import androidx.annotation.Px

interface IXTab<D>: IXTabLayout.OnTabSelectedListener<D> {

    fun setXTabInfo( data: D)

    /**
     * 动态设置某个item的大小
     */
    fun resetHeight(@Px height: Int)
}