package com.wushu.cn

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.xm.lib.common.log.BKLog
import com.xm.lib.common.util.AppUtil

/**
 * 看门狗服务监测用户是否打开其他应用
 */
class WatchDogService : Service() {

    private var skipPackages = ArrayList<String>()
    private var watchDagRunnable: WatchDagRunnable? = null
    private var skipReceiver: SkipReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (watchDagRunnable == null) {
            watchDagRunnable = WatchDagRunnable(applicationContext)
            Thread(watchDagRunnable).start()
        }
        if (skipReceiver == null) {
            skipReceiver = SkipReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("")
            intentFilter.addCategory("")
            applicationContext.registerReceiver(skipReceiver, intentFilter)
        }

        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        if (skipReceiver != null) {
            applicationContext.unregisterReceiver(skipReceiver)
        }
    }

    /**
     * 看门狗Runnable
     */
    class WatchDagRunnable(private val applicationContext: Context) : Runnable {
        private var flag = true

        override fun run() {
            while (flag) {
                Thread.sleep(5000)
                // 判断是否启动了任务，再对比被拦截App的包名，选择是否调起锁屏界面。
                val pkg = AppUtil.getTopActivity(applicationContext)
                if (pkg == "com.ponko.cn") {
                    val intent = Intent(applicationContext, LockActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                }
                BKLog.d("pkg:$pkg")
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