package com.future.awaker.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.future.awaker.R;
import com.future.awaker.news.fragment.CommentListFragment;

/**
 * Copyright ©2017 by ruzhan
 */

public class CommentListActivity extends AppCompatActivity {

    private static final String NEW_ID = "newId";

    private CommentListFragment fragment;

    public static void launch(Context context, String newId) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra(NEW_ID, newId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        String newId = getIntent().getStringExtra(NEW_ID);
        if (TextUtils.isEmpty(newId)) {
            finish();
        }
        if (fragment == null) {
            fragment = CommentListFragment.newInstance(newId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fl, fragment,
                            CommentListFragment.class.getSimpleName())
                    .commit();
        }
    }
}
