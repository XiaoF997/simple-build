package com.xfg.simplebuildpro.refresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.simplebuildpro.R
import com.xfg.simple_build.refresh.XRefreshLayout
import com.xfg.simple_build.refresh.XTextOverView
import com.xfg.simple_build.refresh.interfaces.IXRefresh

class RefreshDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_demo)

        val refreshLayout = findViewById<XRefreshLayout>(R.id.refreshlayout)
        val textOverView = XTextOverView(this)
        refreshLayout.setRefreshOverView(textOverView)
        refreshLayout.setRefreshListener(object : IXRefresh.XRefreshListener {
            override fun onRefresh() {
                Handler().postDelayed({refreshLayout.refreshFinished()}, 2000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }

        })
    }
}