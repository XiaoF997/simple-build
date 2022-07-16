package com.xfg.simple_build.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.xfg.simple_build.utils.DisplayUtil

abstract class XOverView: FrameLayout {

    enum class XRefreshState{
        /**
         * 初始态
         */
        STATE_INIT,
        /**
         * Header展示状态
         */
        STATE_VISIBLE,
        /**
         * 超出可刷新距离的状态
         */
        STATE_REFRESH,

        /**
         * 超出可刷新距离
         */
        STATE_OVER,

        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }

    var mState = XRefreshState.STATE_INIT

    /**
     * 出发下拉刷新的最小高度
     */
    var mPullRefreshHeight: Int = 0

    /**
     * 最小阻尼
     */
    var minDamp:Float = 1.6f

    var maxDamp:Float = 2.2f


    constructor(context: Context) : super(context){

        preInit()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        preInit()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        preInit()
    }

    private fun preInit(){
        mPullRefreshHeight = DisplayUtil.dp2px(66f, resources)
        init()
    }


    abstract fun init()

    abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)

    /**
     * 显示overlay
     */
    abstract fun onVisible()

    /**
     * 超过overlay,释放就会加载
     */
    abstract fun onOver()

    /**
     * 正在刷新
     */
    abstract fun onRefresh()

    /**
     * 刷新完成
     */
    abstract fun onFinish()


}
