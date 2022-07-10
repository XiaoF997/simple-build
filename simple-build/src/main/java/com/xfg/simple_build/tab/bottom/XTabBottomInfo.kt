package com.xfg.simple_build.tab.bottom

import android.graphics.Bitmap
import androidx.fragment.app.Fragment

class XTabBottomInfo<Color>{
    constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap){
        this.name = name
        this.defaultBitmap = defaultBitmap
        this.selectedBitmap = selectedBitmap
        this.tabType = TabType.BITMAP

    }

    constructor(
        iconFont: String,
        name: String,
        defaultIconName: String,
        selectedIconName: String,
        defaultColor: Color?,
        tintColor: Color?
    ) {
        this.iconFont = iconFont
        this.name = name
        this.defaultIconName = defaultIconName
        this.selectedIconName = selectedIconName
        this.defaultColor = defaultColor
        this.tintColor = tintColor
        this.tabType = TabType.ICON
    }


    enum class TabType{
        BITMAP, ICON
    }

    var fragment: Class<in Fragment>? = null
    var iconFont: String = ""
    var name: String = ""
    var defaultBitmap: Bitmap? = null
    var selectedBitmap: Bitmap? = null


    var defaultIconName: String = ""
    var selectedIconName: String = ""
    var defaultColor: Color? = null
    var tintColor: Color? = null
    var tabType: TabType? = null




}