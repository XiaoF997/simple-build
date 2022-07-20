package com.xfg.simplebuildpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.simplebuildpro.R
import com.xfg.simple_build.tab.common.IXTabLayout
import com.xfg.simple_build.tab.top.XTabTopInfo
import com.xfg.simple_build.tab.top.XTabTopLayout
import com.xfg.simplebuildpro.banner.BannerDemoActivity
import com.xfg.simplebuildpro.refresh.RefreshDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "测i大家", Toast.LENGTH_SHORT).show()

        findViewById<Button>(R.id.refresh_overview).setOnClickListener {
            startActivity(Intent(this, RefreshDemoActivity::class.java))

        }
        findViewById<Button>(R.id.xbannerdemo).setOnClickListener {

            startActivity(Intent(this, BannerDemoActivity::class.java))

        }
    }

    override fun onClick(v: View?) {
        Toast.makeText(this, "测i大家", Toast.LENGTH_SHORT).show()

        when(v!!.id){
            R.id.xbannerdemo -> {
            }
            }
    }
}