package com.wushu.cn.db

import android.graphics.drawable.Drawable

/**
 * 数据库操作实体bean
 */
class LockDbBean /*: AppInfoBean()*/ {
    var icon: Drawable? = null
    var appName: String? = null
    var packageName: String? = null
    var versionName: String? = null
    /**
     * 0 系统 1非系统
     */
    var system: Int = 0
    /**
     * 0 false 未选中 1 true
     */
    var choose: Int = 0

    override fun toString(): String {
        return "LockDbBean(icon=$icon, appName=$appName, packageName=$packageName, versionName=$versionName, system=$system, choose=$choose)"
    }
}