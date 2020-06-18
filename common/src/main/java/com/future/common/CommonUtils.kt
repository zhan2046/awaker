package com.future.common

import android.app.Application

class CommonUtils {

    companion object {

        @Volatile
        private var INSTANCE: CommonUtils? = null

        @JvmStatic
        fun get(): CommonUtils = INSTANCE ?: synchronized(CommonUtils::class) {
            INSTANCE ?: CommonUtils().also {
                INSTANCE = it
            }
        }
    }

    private var application: Application? = null
    private var commonViewModel: CommonViewModel? = null

    fun init(application: Application) {
        this.application = application
    }

    fun getContext(): Application = application!!

    fun getString(strId: Int): String {
        return if (strId <= 0) {
            ""
        } else application!!.resources.getString(strId) ?: ""
    }

    fun setCommonViewModel(commonViewModel: CommonViewModel) {
        this.commonViewModel = commonViewModel
    }

    fun getCommonViewModel() = commonViewModel!!
}