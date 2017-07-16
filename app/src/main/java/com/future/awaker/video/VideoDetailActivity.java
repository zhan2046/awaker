package com.future.awaker.video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.future.awaker.R;
import com.future.awaker.databinding.ActivityVideoDetailBinding;

/**
 * Created by ruzhan on 2017/7/16.
 */

public class VideoDetailActivity extends AppCompatActivity {

    private static final String VIDEO_HTML = "videoHtml";
    private VideoDetailFragment videoDetailFragment;

    public static void launch(Context context, String html) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(VIDEO_HTML, html);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityVideoDetailBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_video_detail);

        setStatusBarColor(Color.parseColor("#66808080"));
        String videoHtml = getIntent().getStringExtra(VIDEO_HTML);
        if (TextUtils.isEmpty(videoHtml)) {
            finish();
        }

        if (videoDetailFragment == null) {
            videoDetailFragment = VideoDetailFragment.newInstance(videoHtml);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_fl, videoDetailFragment, VideoDetailFragment.class.getSimpleName())
                    .commit();
        }
    }


    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }
}
