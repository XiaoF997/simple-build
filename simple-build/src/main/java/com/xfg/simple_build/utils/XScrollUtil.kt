package com.xfg.simple_build.utils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class XScrollUtil {
    companion object{

        /**
         * 判断child是否发生了滚动
         * true为发生了滚动
         */
        fun childScrolled(child: View): Boolean{
            if (child is AdapterView<*>){
                if (child.firstVisiblePosition != 0 || child.firstVisiblePosition == 0 && child.getChildAt(0) != null){
                    return true
                }
            }else if(child.scrollY > 0){
                return true
            }
            if (child is RecyclerView){
                val view = child.getChildAt(0)
                val firstPosition = child.getChildAdapterPosition(view)
                Log.d("---top", "${view.top}")
                return firstPosition != 0 || view.top != 0
            }
            return false
        }

        fun findScrollAbleChild(viewGroup: ViewGroup): View? {
            var child = viewGroup.getChildAt(1)
            if (child is RecyclerView || child is AdapterView<*>){
                return child
            }
            if (child is ViewGroup){
                val tempChild = (child as ViewGroup).getChildAt(0)
                if (tempChild is RecyclerView || tempChild is AdapterView<*>){
                    child = tempChild
                }
            }
            return child
        }

    }

}