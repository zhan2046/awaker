package com.future.awaker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.future.awaker.R;
import com.future.awaker.databinding.ActivityMainBinding;
import com.future.awaker.news.NewActivity;

/**
 * Created by ruzhan on 2017/7/12.
 */

public class MainActivity extends AppCompatActivity {

  private static final long DELAY_MILLIS = 2500;

  private ActivityMainBinding binding;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    Glide.with(this).load(R.mipmap.main_start_icon).into(binding.mainIv);
    binding.mainTv.setText(R.string.launch_desc);
    binding.mainIv.postDelayed(() -> {
      NewActivity.launch(this);
      finish();
    }, DELAY_MILLIS);
  }

  @Override
  public void onBackPressed() {

  }
}
