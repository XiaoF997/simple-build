package com.xfg.simplebuildpro.banner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.simplebuildpro.R
import com.xfg.simple_build.banner.XBanner
import com.xfg.simple_build.banner.adapter.IBindAdapter
import com.xfg.simple_build.banner.adapter.XBannerAdapter
import com.xfg.simple_build.banner.core.XBannerMo
import com.xfg.simple_build.banner.indicator.IXIndicator
import com.xfg.simple_build.banner.indicator.XCircleIndicator
import com.xfg.simple_build.banner.indicator.XNumberIndicator

class BannerDemoActivity : AppCompatActivity() {

    private var urls = arrayOf("https://tse1-mm.cn.bing.net/th/id/OIP-C.wc_dCG_KbIKZwMdtD3gL2QHaEt?w=240&h=180&c=7&r=0&o=5&pid=1.7",
    "https://tse1-mm.cn.bing.net/th/id/OIP-C.QPH1IBosDYBqaU3O6wV3YAHaEo?w=258&h=180&c=7&r=0&o=5&pid=1.7",
    "https://tse3-mm.cn.bing.net/th/id/OIP-C.1eFBRKkNrO_OfRQ2KpSTVwHaE7?w=249&h=180&c=7&r=0&o=5&pid=1.7",
    "https://tse3-mm.cn.bing.net/th/id/OIP-C.R_-PqVITRlPichNfhnm5ngHaEo?w=301&h=188&c=7&r=0&o=5&pid=1.7")

    private var autoPlay: Boolean = false

    private var xIndicator: IXIndicator<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_demo)

        xIndicator = XCircleIndicator(this)
        initView(xIndicator, autoPlay)
        findViewById<Switch>(R.id.switch_loop).setOnCheckedChangeListener{_, isChecked ->
            autoPlay = isChecked
            initView(xIndicator, autoPlay)
        }
        findViewById<View>(R.id.switch_ind).setOnClickListener{
            if (xIndicator is XCircleIndicator){
                initView(XNumberIndicator(this), autoPlay)
            }else{
                initView( XCircleIndicator(this), autoPlay)
            }
        }
    }

    private fun initView(xIndicator:IXIndicator<*>?, autoPlay: Boolean) {
        val xBanner = findViewById<XBanner>(R.id.xbanner1)
        val moList = mutableListOf<XBannerMo>()
        for (i in urls){
            val mo = BannerMo(i)
            moList.add(mo)
        }
        xBanner.apply {
            xIndicator?.let { setXIndicator(it) }
            setBannerData(R.layout.banner_item_layout, moList)
            setAutoPlay(autoPlay)
            setIntervalTime(5000)

            setBindAdapter(
                object : IBindAdapter {
                    override fun bind(
                        viewHolder: XBannerAdapter.Companion.XBannerViewHolder,
                        mo: XBannerMo,
                        position: Int
                    ) {
                        val imageView = viewHolder.findViewById<ImageView>( R.id.iv_image)
                        imageView?.let { Glide.with(this@BannerDemoActivity).load(mo.url).into(it) }
                        val title = viewHolder.findViewById<TextView>(R.id.tv_title)
                        title.let { mo.url }
                    }

                } )
        }
    }
}