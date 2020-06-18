package com.future.awaker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.future.common.CommonModel
import com.future.common.CommonViewModel
import com.future.day.image.DayImageDetailFragment
import com.future.movie.detail.fragment.ImageDetailFragment
import com.future.movie.detail.fragment.MovieDetailFragment
import com.future.movie.video.VideoFragment

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
                CommonViewModel.FRAG_MOVIE_DETAIL -> {
                    val movieDetailFragment =
                        MovieDetailFragment.newInstance(commonModel.bundle)
                    addFragment(movieDetailFragment, "MovieDetailFragment",
                        supportFragmentManager)
                }
                CommonViewModel.FRAG_MOVIE_IMAGE_DETAIL -> {
                    val imageDetailFragment =
                        ImageDetailFragment.newInstance(commonModel.bundle)
                    addFragment(imageDetailFragment, "ImageDetailFragment",
                        supportFragmentManager)
                }
                CommonViewModel.FRAG_MOVIE_VIDEO -> {
                    val videoFragment =
                        VideoFragment.newInstance(commonModel.bundle)
                    addFragment(videoFragment, "VideoFragment",
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
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .add(R.id.container, frag, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun replaceFragment(frag: Fragment, tag: String,
                                supportFragmentManager: FragmentManager) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, frag, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}