package com.wushu.cn.db

import android.content.Context
import com.xm.lib.common.bean.AppInfoBean
import com.xm.lib.common.helper.AbsDBHelp

/**
 * 数据库帮助类
 */
class LockDBHelp(context: Context?, name: String?, version: Int) : AbsDBHelp(context, name, version) {

    override fun getCreateTables(): HashMap<String, Any> {
        val table = HashMap<String, Any>()
        table["lock"] = LockDbBean()
        return table
    }

}