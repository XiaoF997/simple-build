package com.xfg.simplebuildpro.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplebuildpro.R
import com.xfg.simple_build.tab.common.IXTabLayout
import com.xfg.simple_build.tab.top.XTabTopInfo
import com.xfg.simple_build.tab.top.XTabTopLayout

class XTabTopDemoActivity : AppCompatActivity() {
    val strList = listOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家具",
        "装修",
        "运动",
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xtab_top_demo)



        val tapLayout = findViewById<XTabTopLayout>(R.id.tab_top_layout)
        val infoList = mutableListOf<XTabTopInfo<*>>()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomTintColor)

        strList.forEach {
            val info = XTabTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }
        tapLayout.inflateInfo(infoList)
        tapLayout.addTabSelectedChangeListener(object : IXTabLayout.OnTabSelectedListener<XTabTopInfo<*>> {
            override fun onTabSelectedListener(
                index: Int,
                prevInfo: XTabTopInfo<*>?,
                nextInfo: XTabTopInfo<*>
            ) {
                Toast.makeText(this@XTabTopDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
            }

        })
        tapLayout.defaultSelected(infoList[0])
    }
}