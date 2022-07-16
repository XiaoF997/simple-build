package com.xfg.simple_build.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.xfg.simple_build.refresh.interfaces.IXRefresh
import com.xfg.simple_build.utils.XScrollUtil
import kotlin.coroutines.coroutineContext
import kotlin.math.abs

class XRefreshLayout : FrameLayout, IXRefresh {
    private var mState = XOverView.XRefreshState.STATE_INIT

    private lateinit var mGestureDetector: GestureDetector

    private lateinit var listener: IXRefresh.XRefreshListener

    private var mXOverView: XOverView? = null

    private var mLastY: Int = 0

    private val autoScroller: AutoScroll = AutoScroll()

    //刷新时是否禁止滚动
    private var disableRefreshScroll = false


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {
        mGestureDetector = GestureDetector(context, xGestureDetector)
    }

    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this.disableRefreshScroll = disableRefreshScroll
    }

    override fun refreshFinished() {
        val head = getChildAt(0)
        mXOverView?.onFinish()
        mXOverView?.mState = XOverView.XRefreshState.STATE_INIT
        val bottom = head.bottom
        if (bottom > 0) {
            recover(bottom)
        }
        mState = XOverView.XRefreshState.STATE_INIT
    }

    override fun setRefreshListener(listener: IXRefresh.XRefreshListener) {
        this.listener = listener
    }

    override fun setRefreshOverView(xOverView: XOverView) {
        if (this.mXOverView != null) {
            removeView(this.mXOverView)
        }
        this.mXOverView = xOverView
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mXOverView, 0, params)

    }

    private val xGestureDetector: XGestureDetector = object : XGestureDetector() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (abs(distanceX) > abs(distanceY) || listener != null && !listener.enableRefresh()) {
                //横向滑动或刷新被禁止则不处理
                return false
            }
            if (disableRefreshScroll && mState == XOverView.XRefreshState.STATE_REFRESH) {
                return true
            }
            val head = getChildAt(0)
            val child: View? = XScrollUtil.findScrollAbleChild(this@XRefreshLayout)
            if (XScrollUtil.childScrolled(child!!)) {
                return false
            }

            if ((mState != XOverView.XRefreshState.STATE_REFRESH || head.bottom <= mXOverView!!.mPullRefreshHeight) && (head.bottom > 0 || distanceY <= 0)) {
                //如果还在滑动中
                return if (mState != XOverView.XRefreshState.STATE_OVER_RELEASE) {
                    var seed = 0
                    seed = if (child.top < mXOverView!!.mPullRefreshHeight) {
                        (mLastY / mXOverView!!.minDamp).toInt()
                    } else {
                        (mLastY / mXOverView!!.maxDamp).toInt()
                    }

                    val bool = moveDown(seed, true)
                    mLastY = (-distanceY).toInt()
                    bool
                } else {
                    false
                }
            } else {
                return false
            }
        }
    }

    private fun moveDown(seed: Int, nonAuto: Boolean): Boolean {
        var offsetY = seed
        val head = getChildAt(0)
        val child = getChildAt(1)
        val childTop = child.top + offsetY
        if (childTop <= 0) {
            offsetY = -child.top
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (mState != XOverView.XRefreshState.STATE_REFRESH) {
                mState = XOverView.XRefreshState.STATE_INIT
            }
        } else if (mState == XOverView.XRefreshState.STATE_REFRESH && childTop > mXOverView!!.mPullRefreshHeight) {
            return false
        } else if (childTop <= mXOverView!!.mPullRefreshHeight) {
            if (mXOverView!!.mState != XOverView.XRefreshState.STATE_VISIBLE && nonAuto) {
                mXOverView?.onVisible()
                mXOverView?.mState = XOverView.XRefreshState.STATE_VISIBLE
                mState = XOverView.XRefreshState.STATE_VISIBLE
            }

            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (childTop == mXOverView!!.mPullRefreshHeight && mState == XOverView.XRefreshState.STATE_OVER_RELEASE) {
                //刷新完成
                refresh()
            }

        } else {
            if (mXOverView!!.mState != XOverView.XRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mXOverView?.onOver()
                mXOverView?.mState = XOverView.XRefreshState.STATE_OVER
            }

            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
        }

        mXOverView?.onScroll(head.bottom, mXOverView!!.mPullRefreshHeight)
        return true
    }

    private fun refresh() {
        mState = XOverView.XRefreshState.STATE_REFRESH
        mXOverView?.onRefresh()
        mXOverView?.mState = XOverView.XRefreshState.STATE_REFRESH
        listener.onRefresh()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val head = getChildAt(0)
        val child = getChildAt(1)
        if (head != null || child != null) {
            val childTop = child.top
            if (mState == XOverView.XRefreshState.STATE_REFRESH) {
                head.layout(
                    0,
                    mXOverView!!.mPullRefreshHeight - head.measuredHeight,
                    right,
                    mXOverView!!.mPullRefreshHeight
                )
                child.layout(
                    0,
                    mXOverView!!.mPullRefreshHeight,
                    right,
                    mXOverView!!.mPullRefreshHeight + child.measuredHeight
                )
            } else {
                head.layout(0, childTop - head.measuredHeight, right, childTop)
                child.layout(0, childTop, right, childTop + child.measuredHeight)
            }
            for (i in 2..childCount) {
                var other = getChildAt(i)
                other?.let {
                    it.layout(0, top, right, bottom)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val header = getChildAt(0)
        if (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_CANCEL || ev?.action == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //松手
            if (header.bottom > 0) {
                if (mState != XOverView.XRefreshState.STATE_REFRESH) {//不是正在刷新
                    recover(header.bottom)
                    return false
                }
            }
            mLastY = 0
        }
        val consumed = mGestureDetector.onTouchEvent(ev)
        if ((consumed || (mState != XOverView.XRefreshState.STATE_INIT && mState != XOverView.XRefreshState.STATE_REFRESH)) && header.bottom != 0) {
            ev?.action = MotionEvent.ACTION_CANCEL
            return super.dispatchTouchEvent(ev)
        }
        return if (consumed) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    private fun recover(bottom: Int) {
        if (listener != null && bottom > mXOverView!!.mPullRefreshHeight) {
            //滚动到指定位置
            autoScroller.recover(bottom - mXOverView!!.mPullRefreshHeight)
            mState = XOverView.XRefreshState.STATE_OVER_RELEASE


        } else {
            autoScroller.recover(bottom)
        }
    }

    private inner class AutoScroll : Runnable {
        private var scroller: Scroller = Scroller(context, LinearInterpolator())
        private var mLastY = 0
        var mIsFinished = true
            private set

        init {
            mIsFinished = true
        }


        override fun run() {
            if (scroller.computeScrollOffset()) {
                moveDown(mLastY - scroller.currY, false)
                mLastY = scroller.currY
                post(this)
            } else {
                removeCallbacks(this)
                mIsFinished = true
            }
        }

        fun recover(bottom: Int) {
            if (bottom < 0) {
                return
            }
            removeCallbacks(this)
            mLastY = 0
            mIsFinished = false
            scroller.startScroll(0, 0, 0, bottom, 300)
            post (this)
        }

    }
}