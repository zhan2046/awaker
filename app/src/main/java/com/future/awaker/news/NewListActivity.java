package com.future.awaker.news;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.future.awaker.R;
import com.future.awaker.base.BaseActivity;
import com.future.awaker.databinding.ActivityNewListBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class NewListActivity extends BaseActivity {

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE = "newTitle";

    private NewListFragment newListFragment;
    private ActivityNewListBinding binding;

    public static void launch(Context context, int newId, String title) {
        Intent intent = new Intent(context, NewListActivity.class);
        intent.putExtra(NEW_ID, newId);
        intent.putExtra(NEW_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_list);

        int newId = getIntent().getIntExtra(NEW_ID, 0);
        String newTitle = getIntent().getStringExtra(NEW_TITLE);
        if (newId < 0) {
            finish();
        }

        binding.toolbar.setTitle(newTitle);
        setToolbar(binding.toolbar);
        if (newListFragment == null) {
            newListFragment = NewListFragment.newInstance(newId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fl, newListFragment,
                            NewListFragment.class.getSimpleName())
                    .commit();
        }
    }
}
