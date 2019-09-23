package com.wushu.cn.db

import android.content.Context
import com.xm.lib.common.bean.AppInfoBean
import com.xm.lib.common.helper.AbsDBHelp

/**
 * 数据库帮助类
 */
class LockDBHelp(context: Context?, name: String?, version: Int) : AbsDBHelp(context, name, version) {

    var lockSqlStatementCreation: LockSqlStatementCreation? = LockSqlStatementCreation()

    override fun getCreateTables(): ArrayList<String> {
        val createTables = ArrayList<String>()

        //如果传入对象是Any就会出问题:getShadow$_klass_ []
        createTables.add(lockSqlStatementCreation?.createSQLTable(AppInfoBean(), "lock")!!)

        return createTables
    }

}