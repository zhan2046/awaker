package com.future.common

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {

    companion object {

        const val FRAG_IMAGE_DETAIL = "FRAG_IMAGE_DETAIL"

        const val FRAG_MOVIE_DETAIL = "FRAG_MOVIE_DETAIL"
        const val FRAG_MOVIE_IMAGE_DETAIL = "FRAG_MOVIE_IMAGE_DETAIL"
        const val FRAG_MOVIE_VIDEO = "FRAG_MOVIE_VIDEO"

        const val POP_BACK_STACK = "POP_BACK_STACK"

        private val DEFAULT_COMMON_MODEL: CommonModel = CommonModel()
    }

    val addFragmentLiveData = MutableLiveData<CommonModel>()

    fun popBackStack() {
        resetCommonModel()
        DEFAULT_COMMON_MODEL.key = POP_BACK_STACK
        addFragmentLiveData.value = DEFAULT_COMMON_MODEL
    }

    fun addFragment(fragKey: String) {
        resetCommonModel()
        DEFAULT_COMMON_MODEL.key = fragKey
        addFragmentLiveData.value = DEFAULT_COMMON_MODEL
    }

    fun addFragment(fragKey: String, bundle: Bundle) {
        resetCommonModel()
        DEFAULT_COMMON_MODEL.key = fragKey
        DEFAULT_COMMON_MODEL.bundle = bundle
        addFragmentLiveData.value = DEFAULT_COMMON_MODEL
    }

    private fun resetCommonModel() {
        DEFAULT_COMMON_MODEL.key = ""
        DEFAULT_COMMON_MODEL.bundle = null
    }
}