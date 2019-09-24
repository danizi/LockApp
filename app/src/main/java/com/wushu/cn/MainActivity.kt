package com.wushu.cn

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wushu.cn.contract.MainContract
import com.wushu.cn.db.LockDbBean
import com.wushu.cn.holder.LockVH
import com.xm.lib.common.base.mvp.MvpActivity
import com.xm.lib.common.base.rv.decoration.MyDividerItemDecoration
import com.xm.lib.common.base.rv.v2.BaseRvAdapterV2
import com.xm.lib.common.bean.AppInfoBean
import com.xm.lib.common.log.BKLog

/**
 * 主Activity
 */
class MainActivity : MvpActivity<MainContract.P>(), MainContract.V {

    private var adapter: BaseRvAdapterV2? = null
    private var ui: MainContract.V.UI? = null

    override fun presenter(): MainContract.P {
        return MainContract.P(this, this)
    }

    override fun setContentViewBefore() {}

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun findViews() {
        if (ui == null) {
            ui = MainContract.V.UI.create(this)
        }
    }

    override fun initDisplay() {
        ui?.progressCircular?.visibility = View.GONE
        ui?.rv?.layoutManager = LinearLayoutManager(this)
        ui?.rv?.addItemDecoration(MyDividerItemDecoration.divider(this, MyDividerItemDecoration.HORIZONTAL, R.drawable.shape_question_diveder_1))
    }

    override fun iniData() {
        //获取系统内容
        p?.requestAppInfoList()
        //开启一个看门狗服务
        p?.startWatchDogService()

        val lock = LockDbBean()
        lock.icon = BitmapDrawable()
        lock.packageName = "packageName1234124"
        lock.appName = "appName1321324"
        lock.choose = 1
        lock.system = 1
        lock.versionName = "2.0.0"
        LockApplication.lockDao?.insert(lock)
        LockApplication.lockDao?.select(lock)
        //LockApplication.lockDao?.delete(lock)
    }

    override fun iniEvent() {
        ui?.srl?.setOnRefreshListener {
            p?.refresh()
        }
    }

    override fun setAdapter(appInfoList: ArrayList<AppInfoBean>?) {
        adapter = BaseRvAdapterV2
                .Builder()
                .addDataResouce(appInfoList as ArrayList<Any>)
                .addHolderFactory(LockVH.Factory())
                .build()
        ui?.rv?.adapter = adapter
    }

    override fun refreshRvData(appInfoList: ArrayList<AppInfoBean>) {
        adapter?.getDataSource()?.clear()
        adapter?.getDataSource()?.addAll(appInfoList)
    }

    override fun getAppInfoListFailure(message: String?) {
        BKLog.e("获取信息列表失败，原因 : $message")
    }

    override fun refreshFailure(message: String?) {
        BKLog.e("刷新信息列表失败，原因 : $message")
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
