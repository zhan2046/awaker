package com.future.awaker.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.future.awaker.base.listener.DebouncingOnClickListener;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class BaseActivity extends AppCompatActivity {



    protected void setToolbar(Toolbar toolbar) {
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
