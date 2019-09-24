package com.wushu.cn.db

import com.xm.lib.common.helper.DBContract

/**
 * 数据库操作dao
 */
class LockDao(private var tableName: String, private var lockDBHelp: LockDBHelp?) : DBContract.AbsDao<LockDbBean>(tableName, lockDBHelp) {

    override fun newInstance(): LockDbBean {
        return LockDbBean()
    }

    override fun getDeleteSelectionValaue(bean: LockDbBean?): Array<Any?> {
        return arrayOf(bean?.packageName)
    }

    override fun getSelectSelectionValue(bean: LockDbBean?): Array<String?> {
        return arrayOf(bean?.packageName)
    }

    override fun getSelectSelection(): String {
        return "packageName=?"
    }

    override fun getDeleteSelection(): String {
        return "packageName=?"
    }

    override fun getUpdateSelection(): String {
        return "packageName=?"
    }
}