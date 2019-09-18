package com.wushu.cn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * 提醒用户执行任务过程请勿打开其他应用
 */
class LockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
    }
}
