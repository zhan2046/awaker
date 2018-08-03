package com.future.awaker.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.future.awaker.R;
import com.future.awaker.databinding.ActivityHomeBinding;
import com.future.awaker.home.adapter.HomeAdapter;
import com.future.awaker.util.ResUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeActivity extends AppCompatActivity {

    private static final int BACK_TIME = 2000;

    private ActivityHomeBinding binding;
    private long firstTime = 0;
    private HomeAdapter homeAdapter;


    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setTitle(R.string.app_name);
        setSupportActionBar(binding.toolbar);

        List<String> titles = Arrays.asList(ResUtils.getString(R.string.home),
                ResUtils.getString(R.string.news),
                ResUtils.getString(R.string.video));

        homeAdapter = new HomeAdapter(getSupportFragmentManager(), titles);
        binding.viewpager.setAdapter(homeAdapter);
        binding.viewpager.setOffscreenPageLimit(titles.size());
        binding.viewpager.setCurrentItem(HomeAdapter.NEW, false);
        binding.tabs.setupWithViewPager(binding.viewpager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < BACK_TIME) {
                System.exit(0);
            } else {
                Toast.makeText(HomeActivity.this, R.string.exit_app, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
