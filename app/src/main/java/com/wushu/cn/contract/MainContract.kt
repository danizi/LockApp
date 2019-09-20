package com.wushu.cn.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.TextView
import com.wushu.cn.R
import com.xm.lib.common.bean.AppInfoBean
import com.xm.lib.common.util.AppUtil
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.wushu.cn.WatchDogService
import com.xm.lib.common.log.BKLog

/**
 * 主界面契约类
 */
class MainContract {

    interface V {
        /**
         * 为RecyclerView设置适配器
         */
        fun setAdapter(appInfoList: ArrayList<AppInfoBean>?)

        /**
         * 获取列表失败
         */
        fun getAppInfoListFailure(message: String?)

        /**
         * 刷新成功
         */
        fun refreshRvData(appInfoList: ArrayList<AppInfoBean>)

        /**
         * 刷新是失败
         */
        fun refreshFailure(message: String?)

        /**
         * 主界面UI控件
         */
        class UI private constructor(val searchView: SearchView, val tvLockTip: TextView, val srl: SwipeRefreshLayout, val rv: RecyclerView, val progressCircular: ContentLoadingProgressBar) {
            companion object {

                fun create(rootView: Activity): UI {
                    val searchView = rootView.findViewById<View>(R.id.searchView) as SearchView
                    val tvLockTip = rootView.findViewById<View>(R.id.tv_lock_tip) as TextView
                    val srl = rootView.findViewById<View>(R.id.srl) as SwipeRefreshLayout
                    val rv = rootView.findViewById<View>(R.id.rv) as RecyclerView
                    val progressCircular = rootView.findViewById<View>(R.id.progress_circular) as ContentLoadingProgressBar
                    return UI(searchView, tvLockTip, srl, rv, progressCircular)
                }
            }
        }
    }

    class M {

        private val mainHandler = Handler(Looper.getMainLooper())

        interface IAppInfoListener {
            fun onSuccess(appInfoList: ArrayList<AppInfoBean>)
            fun onFailure(e: Exception)
        }

        /**
         * 获取手机系统安装应用信息列表
         */
        fun getAppInfoList(ctx: Context?, call: IAppInfoListener?) {
            Thread(Runnable {
                var appInfoList: ArrayList<AppInfoBean>? = null
                try {
                    appInfoList = AppUtil.getApps(ctx?.packageManager!!)
                    mainHandler.post {
                        call?.onSuccess(appInfoList)
                    }
                } catch (e: Exception) {
                    mainHandler.post {
                        call?.onFailure(e)
                    }
                }
            }).start()
        }
    }

    class P(private val ctx: Context?, private val v: V?) {

        private val m = M()

        fun requestAppInfoList() {
            m.getAppInfoList(ctx, object : M.IAppInfoListener {
                override fun onSuccess(appInfoList: ArrayList<AppInfoBean>) {
                    if (appInfoList.isNotEmpty()) {
                        v?.setAdapter(appInfoList)
                    } else {
                        BKLog.e("加载获取appInfoList数据为null")
                    }
                }

                override fun onFailure(e: Exception) {
                    v?.getAppInfoListFailure(e.message)
                }
            })
        }

        fun refresh() {
            m.getAppInfoList(ctx, object : M.IAppInfoListener {
                override fun onSuccess(appInfoList: ArrayList<AppInfoBean>) {
                    if (appInfoList.isNotEmpty()) {
                        v?.refreshRvData(appInfoList)
                    } else {
                        BKLog.e("刷新获取appInfoList数据为null")
                    }
                }

                override fun onFailure(e: Exception) {
                    v?.refreshFailure(e.message)
                }
            })
        }

        fun startWatchDogService() {
            ctx?.startService(Intent(ctx, WatchDogService::class.java))
        }
    }
}