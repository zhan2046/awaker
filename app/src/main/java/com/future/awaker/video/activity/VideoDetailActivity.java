package com.future.awaker.video.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.future.awaker.R;
import com.future.awaker.video.fragment.VideoDetailFragment;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.container);
        String videoHtml = getIntent().getStringExtra(VIDEO_HTML);
        if (TextUtils.isEmpty(videoHtml)) {
            finish();
        }

        if (videoDetailFragment == null) {
            videoDetailFragment = VideoDetailFragment.newInstance(videoHtml);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, videoDetailFragment, VideoDetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
