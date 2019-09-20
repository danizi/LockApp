package com.wushu.cn.contract

import android.content.Context

/**
 * 倒计时契约类
 */
class TimerContract {
    interface V {}
    class M {}
    class P(private val ctx: Context, v: V) {
        private val m = M()
    }
}