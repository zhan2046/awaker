package com.future.common

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

    private var commonViewModel: CommonViewModel? = null

    fun setCommonViewModel(commonViewModel: CommonViewModel) {
        this.commonViewModel = commonViewModel
    }

    fun getCommonViewModel() = commonViewModel!!
}