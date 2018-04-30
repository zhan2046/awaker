package com.future.awaker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.future.awaker.R;
import com.future.awaker.databinding.ActivityMainBinding;
import com.future.awaker.home.HomeActivity;

/**
 * Created by ruzhan on 2017/7/12.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.rootRl.post(() -> {
            HomeActivity.launch(this);
            finish();
        });
    }

    @Override
    public void onBackPressed() {

    }
}
