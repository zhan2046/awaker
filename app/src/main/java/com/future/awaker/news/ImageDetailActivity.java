package com.future.awaker.news;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.future.awaker.R;
import com.future.awaker.databinding.ActivityImageDetailBinding;
import com.future.awaker.imageloader.ImageLoader;

/**
 * Copyright ©2017 by Teambition
 */

public class ImageDetailActivity extends AppCompatActivity {

    private static final String IMG = "img";

    private ActivityImageDetailBinding binding;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(IMG, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail);

        String url = getIntent().getStringExtra(IMG);
        if (TextUtils.isEmpty(url)) {
            finish();
        }
        ImageLoader.get().load(binding.photoView, url, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target<Drawable> target, boolean isFirstResource) {
                finish();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model,
                                           Target<Drawable> target, DataSource dataSource,
                                           boolean isFirstResource) {
                binding.progressbar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
