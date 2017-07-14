package com.future.awaker.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.future.awaker.R;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailActivity extends AppCompatActivity {

    private static final String NEW_ID = "newId";

    private NewDetailFragment newDetailFragment;

    public static void launch(Context context, String newId) {
        Intent intent = new Intent(context, NewDetailActivity.class);
        intent.putExtra(NEW_ID, newId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        String newId = getIntent().getStringExtra(NEW_ID);
        if (TextUtils.isEmpty(newId)) {
            finish();
        }
        if (newDetailFragment == null) {
            newDetailFragment = NewDetailFragment.newInstance(newId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fl, newDetailFragment,
                            NewDetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
