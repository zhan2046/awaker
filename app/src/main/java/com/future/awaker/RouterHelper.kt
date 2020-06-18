package com.future.awaker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.future.common.CommonModel
import com.future.common.CommonViewModel
import com.future.day.image.DayImageDetailFragment

object RouterHelper {

    fun handleCommonModel(commonModel: CommonModel?,
                          supportFragmentManager: FragmentManager) {
        if (commonModel != null) {
            when (commonModel.key) {
                CommonViewModel.FRAG_IMAGE_DETAIL -> {
                    val dayImageDetailFragment =
                        DayImageDetailFragment.newInstance(commonModel.bundle)
                    addFragment(dayImageDetailFragment, "DayImageDetailFragment",
                        supportFragmentManager)
                }
                CommonViewModel.POP_BACK_STACK -> {
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun addFragment(frag: Fragment, tag: String,
                            supportFragmentManager: FragmentManager) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, frag, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun replaceFragment(frag: Fragment, tag: String,
                                supportFragmentManager: FragmentManager) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, frag, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}