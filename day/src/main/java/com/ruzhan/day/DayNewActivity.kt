package com.ruzhan.day

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class DayNewActivity :AppCompatActivity() {

    companion object {

        @JvmStatic
        fun launch(activity: Activity) {
            val intent = Intent(activity, DayNewActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_container)
        if (savedInstanceState == null) {
            val dayNewFragment = DayNewFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, dayNewFragment, "DayNewFragment")
                    .commit()
        }
    }
}