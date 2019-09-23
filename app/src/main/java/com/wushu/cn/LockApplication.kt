package com.wushu.cn

import android.app.Application
import com.wushu.cn.db.LockDBHelp
import com.wushu.cn.db.LockDao

/**
 * Application
 */
class LockApplication : Application() {

    companion object {
        /**
         * 数据库操作
         */
        var lockDao: LockDao? = null
    }

    private var lockDBHelp: LockDBHelp? = null


    override fun onCreate() {
        super.onCreate()
        iniDB()
    }

    private fun iniDB() {
        lockDBHelp = LockDBHelp(this, "lock.db", 100)
        lockDao = LockDao(lockDBHelp)
    }
}