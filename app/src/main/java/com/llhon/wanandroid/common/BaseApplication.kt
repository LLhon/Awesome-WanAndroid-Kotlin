package com.llhon.wanandroid.common

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BuildConfig
import com.llhon.wanandroid.R
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smartrefresh.header.DeliveryHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 *
 * Created by LLhon
 */
open class BaseApplication: Application() {

    private lateinit var mRefWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        mRefWatcher = LeakCanary.install(this)

        initGreenDao()


    }

    companion object {
        lateinit var mContext: Context

        //类似于java中的静态代码块
        init {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //kotlin中的lambda表达式
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout ->
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white)
                DeliveryHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, layout: RefreshLayout ->
                //默认是 BallPulseFooter
                BallPulseFooter(context).setAnimatingColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
        }
    }

    private fun initGreenDao() {

    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(getString(R.string.app_name)).build()))
        }
        // TODO: 2021/1/19  
//        Logger.addLogAdapter(DiskLogAdapter(TxtFo))
    }
}