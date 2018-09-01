package com.future.awaker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by ruzhan on 2017/7/12.
 */

class MainActivity : AppCompatActivity() {

    private var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, mainFragment, "MainFragment")
                    .commit()
        }
    }
}
