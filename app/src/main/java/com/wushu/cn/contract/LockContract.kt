package com.wushu.cn.contract

import android.content.Context

/**
 * 锁屏界面契约类
 */
class LockContract {
    interface V {}
    class M {}
    class P(private val ctx: Context, v: V) {
        private val m = M()
    }
}