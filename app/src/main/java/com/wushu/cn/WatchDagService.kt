package com.wushu.cn

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder

/**
 * 看门狗服务监测用户是否打开其他应用
 */
class WatchDagService : Service() {

    private var skipPackages = ArrayList<String>()
    private var watchDagRunable = WatchDagRunnable()
    private var skipReceiver = SkipReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    /**
     * 看门狗Runnable
     */
    class WatchDagRunnable : Runnable {
        private var flag = true

        override fun run() {
            while (flag) {
                // 判断是否启动了任务，再对比被拦截App的包名，选择是否调起锁屏界面。
            }
        }
    }

    /**
     * 接收器
     */
    class SkipReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

        }

    }
}