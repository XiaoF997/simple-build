package com.xfg.simple_build.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.example.simple_build.R
import com.xfg.simple_build.refresh.XOverView

class XTextOverView : XOverView {
    private lateinit var mText: TextView

    private lateinit var mRotateView: View

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun init() {
        LayoutInflater.from(context).inflate(R.layout.x_refresh_overview, this, true)
        mText = findViewById(R.id.tv_text)
        mRotateView = findViewById(R.id.iv_rotate)


    }

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
    }

    override fun onVisible() {
        mText.text = "下拉刷新"
    }

    override fun onOver() {
        mText.text = "松开刷新"
    }

    override fun onRefresh() {
        mText.text = "正在刷新..."
        val operationAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operationAnim.interpolator = lin
        mRotateView.animation = operationAnim
    }

    override fun onFinish() {
        mRotateView.clearAnimation()
    }
}