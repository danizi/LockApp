package com.wushu.cn.db

import android.database.sqlite.SQLiteDatabase
import android.util.SparseArray
import com.xm.lib.common.helper.DBContract
import com.xm.lib.common.log.BKLog

/**
 * 数据库操作dao
 */
class LockDao(private var lockDBHelp: LockDBHelp?) : DBContract.AbsDao<LockDbBean>() {
    private var writableDatabase: SQLiteDatabase? = null
    private var lockSqlStatementCreation: LockSqlStatementCreation? = null
    private val sqls: SparseArray<String>? = null
    private val tableName = "lock"

    init {
        writableDatabase = lockDBHelp?.writableDatabase
        lockSqlStatementCreation = lockDBHelp?.lockSqlStatementCreation
    }

    override fun exist(bean: LockDbBean): Boolean {
        if (select(bean).isNotEmpty()) {
            return true
        }
        return false
    }

    override fun insert(bean: LockDbBean): Boolean {
        try {
            if (exist(bean)) {
                BKLog.d("该记录存在 ${bean.toString()}")
                return false
            }
            val insertSql = lockSqlStatementCreation?.createSQLInsert(bean, tableName)
            writableDatabase?.execSQL(insertSql, arrayOf(
                    bean.icon,
                    bean.appName,
                    bean.packageName,
                    bean.versionName,
                    bean.system,
                    bean.check
            ))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun delete(bean: LockDbBean): Boolean {
        val deleteSql = lockSqlStatementCreation?.createSQLDelete(bean, tableName, "packageName = ?")
        try {
            writableDatabase?.execSQL(deleteSql, arrayOf(bean.packageName))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun update(bean: LockDbBean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(bean: LockDbBean): ArrayList<LockDbBean> {
        //通过包名来查询
        val querySql = lockSqlStatementCreation?.createSQLQuery(bean, tableName, "packageName = ?")
        return select(bean, querySql)
    }

    override fun deleteAll(): Boolean {
        val deleteAllSql = lockSqlStatementCreation?.createSQLDelete(null, tableName, "")
        try {
            writableDatabase?.execSQL(deleteAllSql)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun selectALL(): ArrayList<LockDbBean> {
        val selectAllSql = lockSqlStatementCreation?.createSQLQuery(null, tableName, "")
        return select(LockDbBean(), selectAllSql)
    }

    override fun clear() {
        if (writableDatabase?.isOpen == true) {
            writableDatabase?.close()
        }
        writableDatabase = null
        lockSqlStatementCreation = null
    }

    private fun select(bean: LockDbBean, sql: String?): ArrayList<LockDbBean> {
        check()
        val lockDbBeans = ArrayList<LockDbBean>()

        val cursor = writableDatabase?.rawQuery(sql, arrayOf(bean.packageName))
                ?: return lockDbBeans
        try {
            while (cursor.moveToNext()) {
                val lockDbBean = LockDbBean()
                //cursor.getInt(0)      //主键
                //lockDbBean.icon = cursor.getInt(1)
                lockDbBean.appName = cursor.getString(2)
                lockDbBean.packageName = cursor.getString(3)
                lockDbBean.versionName = cursor.getString(4)
                lockDbBean.system = cursor.getInt(4)
                lockDbBean.check = cursor.getInt(5)
                lockDbBeans.add(lockDbBean)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cursor.close()
        } finally {
            if (cursor.isClosed) {
                cursor.close()
            }
        }
        BKLog.d("select: ${lockDbBeans.toString()}")
        return lockDbBeans
    }

    private fun check() {
        if (writableDatabase?.isOpen != true) {
            throw IllegalAccessException("writableDatabase Did not open")
        }
    }
}