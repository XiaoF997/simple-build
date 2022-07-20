package com.xfg.simple_build.banner.indicator

import android.view.View

interface IXIndicator<T : View> {

    fun get(): T

    fun onInflate(count: Int)

    fun onPointChange(current:Int, count: Int)
}