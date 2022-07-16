package com.xfg.simple_build.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import java.util.*

class XViewUtil {

    companion object{
        /**
         * 获取指定类型的子view
         */
        fun <T> findTypeView(group: ViewGroup, cls: Class<T>): T? {
            val deque:Deque<View> = ArrayDeque()
            deque.add(group)
            while (!deque.isEmpty()){
                val node = deque.removeFirst()
                if (cls.isInstance(node)){
                    return cls.cast(node)
                }else if(node is ViewGroup){
                    val container = node as ViewGroup
                    for (i in container.children){
                        deque.add(i)
                    }
                }
            }
            return null
        }
    }
}