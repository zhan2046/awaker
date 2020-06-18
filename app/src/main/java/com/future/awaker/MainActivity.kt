package com.future.awaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.future.common.CommonUtils
import com.future.common.CommonViewModel

class MainActivity : AppCompatActivity() {

    private val commonViewModel: CommonViewModel by lazy {
        ViewModelProvider(this).get(CommonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        if (savedInstanceState == null) {
            CommonUtils.get().setCommonViewModel(commonViewModel)
            initLiveData()
            val mainFragment = MainFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.fade_out)
                .add(R.id.container, mainFragment, "MainFragment")
                .commitAllowingStateLoss()
        }
    }

    private fun initLiveData() {
        commonViewModel.addFragmentLiveData.observe(this,
            Observer { commonModel ->
                RouterHelper.handleCommonModel(commonModel, supportFragmentManager)
            })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed();
        }
    }
}
