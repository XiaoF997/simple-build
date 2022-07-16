package com.xfg.simplebuildpro.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xfg.simple_build.tab.bottom.XTabBottomInfo
import com.xfg.simple_build.tab.bottom.XTabBottomLayout
import com.xfg.simple_build.tab.common.IXTabLayout
import com.example.simplebuildpro.R
import com.xfg.simple_build.utils.DisplayUtil

class XTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xtab_bottom_demo)

        initTabBottom()
    }

    private fun initTabBottom() {
        val bottomLayout = findViewById<XTabBottomLayout>(R.id.xtablayout)
        bottomLayout.alpha = 0.85f
        val botttomInfoList: MutableList<XTabBottomInfo<*>> = mutableListOf()
        val homeInfo = XTabBottomInfo(
            "fonts/iconfont.ttf",
            "首页",
            getString(R.string.if_home),
            "",
            "#ff656667",
            "#ffd44949"
        )
        val favorite = XTabBottomInfo(
            "fonts/iconfont.ttf",
            "收藏",
            getString(R.string.if_favorite),
            "",
            "#ff656667",
            "#ffd44949"
        )
//        val category = XTabBottomInfo(
//            "fonts/iconfont.ttf",
//            "分类",
//            getString(R.string.if_category),
//            "",
//            "#ff656667",
//            "#ffd44949"
//        )
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.tu, null)
        val category = XTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )
        val recommend = XTabBottomInfo(
            "fonts/iconfont.ttf",
            "推荐",
            getString(R.string.if_recommend),
            "",
            "#ff656667",
            "#ffd44949"
        )
        val mine = XTabBottomInfo(
            "fonts/iconfont.ttf",
            "我的",
            getString(R.string.if_profile),
            "",
            "#ff656667",
            "#ffd44949"
        )

        botttomInfoList.apply {
            add(homeInfo)
            add(favorite)
            add(category)
            add(recommend)
            add(mine)
        }
        bottomLayout.inflateInfo(botttomInfoList)
        bottomLayout.addTabSelectedChangeListener(object : IXTabLayout.OnTabSelectedListener<XTabBottomInfo<*>> {
            override fun onTabSelectedListener(
                index: Int,
                prevInfo: XTabBottomInfo<*>?,
                nextInfo: XTabBottomInfo<*>
            ) {
                Toast.makeText(this@XTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
            }

        })

        bottomLayout.defaultSelected(homeInfo)

        val tabBottom = bottomLayout.findTab(botttomInfoList[2])
        tabBottom?.apply {
            resetHeight(DisplayUtil.dp2px(66f, resources))
        }
    }
}