package com.wushu.cn.holder

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wushu.cn.R
import com.xm.lib.common.base.rv.v2.BaseViewHolderV2
import com.xm.lib.common.bean.AppInfoBean
import android.widget.Switch
import android.widget.TextView
import com.xm.lib.common.log.BKLog

/**
 * 需要拦截锁屏的VH
 */
class LockVH(view: View) : BaseViewHolderV2(view) {

    private var ui: UI? = null

    override fun onBind(data: Any) {
        if (ui == null) {
            ui = UI.create(itemView)
        }
        val ctx = itemView.context
        if (data is AppInfoBean) {
            val appInfo = data as AppInfoBean
            //设置应用icon
            ui?.ivAppIcon?.setImageDrawable(appInfo.icon)
            //设置应用名称
            ui?.tvAppName?.text = appInfo.appName
            //设置应用包名
            ui?.tvAppPackage?.text = appInfo.packageName
            //检查用户之前是否有设置过
            ui?.st?.isChecked = appInfo.isCheck
            //设置监听
            ui?.st?.setOnCheckedChangeListener { buttonView, isChecked ->
                BKLog.d("isChecked:$isChecked")
            }
        } else {
            throw IllegalAccessException("")
        }
    }

    override fun onClick(v: View?) {

    }

    class Factory : BaseViewHolderV2.Factory() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolderV2 {
            return LockVH(getView(parent.context, parent, R.layout.item_lock))
        }

        override fun getItemViewType(): Pair<Class<*>, String> {
            return Pair(AppInfoBean::class.java, this.javaClass.simpleName)
        }
    }

    private class UI private constructor(val ivAppIcon: ImageView, val tvAppName: TextView, val tvAppPackage: TextView, val st: Switch) {
        companion object {

            fun create(rootView: View): UI {
                val ivAppIcon = rootView.findViewById<View>(R.id.iv_app_icon) as ImageView
                val tvAppName = rootView.findViewById<View>(R.id.tv_app_name) as TextView
                val tvAppPackage = rootView.findViewById<View>(R.id.tv_app_package) as TextView
                val st = rootView.findViewById<View>(R.id.st) as Switch
                return UI(ivAppIcon, tvAppName, tvAppPackage, st)
            }
        }
    }

}