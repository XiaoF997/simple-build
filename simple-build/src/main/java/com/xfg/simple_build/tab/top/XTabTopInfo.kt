package com.xfg.simple_build.tab.top

import android.graphics.Bitmap
import androidx.fragment.app.Fragment

class XTabTopInfo<Color> {

    enum class TabType{
        BITMAP, TEXT
    }

    var fragment: Class<in Fragment>? = null
    var name: String = ""
    var defaultBitmap: Bitmap? = null
    var selectedBitmap: Bitmap? = null


    var defaultColor: Color? = null
    var tintColor: Color? = null
    var tabType: TabType? = null

    constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap){
        this.name = name
        this.defaultBitmap = defaultBitmap
        this.selectedBitmap = selectedBitmap
        this.tabType = TabType.BITMAP

    }

    constructor(
        name: String,
        defaultColor: Color?,
        tintColor: Color?
    ) {
        this.name = name
        this.defaultColor = defaultColor
        this.tintColor = tintColor
        this.tabType = TabType.TEXT
    }
}