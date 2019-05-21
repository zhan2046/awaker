package com.future.awaker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        if (savedInstanceState == null) {
            val mainFragment = MainFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, mainFragment, "MainFragment")
                    .commit()
        }
    }
}
