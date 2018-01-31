package com.future.awaker.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.future.awaker.R;
import com.future.awaker.base.BaseActivity;
import com.future.awaker.data.HomeItem;
import com.future.awaker.databinding.ActivityNewListBinding;
import com.future.awaker.news.fragment.HotNewsFragment;
import com.future.awaker.news.fragment.HotReadNewsFragment;
import com.future.awaker.news.fragment.NiceCommentFragment;
import com.future.awaker.news.fragment.OtherNewListFragment;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeTypeListActivity extends BaseActivity<ActivityNewListBinding> {

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE = "newTitle";

    private OtherNewListFragment otherNewListFragment;
    private HotReadNewsFragment hotReadNewsFragment;
    private HotNewsFragment hotNewsFragment;
    private NiceCommentFragment niceCommentFragment;

    public static void launch(Context context, int newId, String title) {
        Intent intent = new Intent(context, HomeTypeListActivity.class);
        intent.putExtra(NEW_ID, newId);
        intent.putExtra(NEW_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_new_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int newId = getIntent().getIntExtra(NEW_ID, 0);
        String newTitle = getIntent().getStringExtra(NEW_TITLE);

        binding.toolbar.setTitle(newTitle);
        setToolbar(binding.toolbar);

        switchPages(newId);
    }

    private void switchPages(int newId) {
        switch (newId) {
            case HomeItem.NICE_COMMENT:
                if (niceCommentFragment == null) {
                    niceCommentFragment = NiceCommentFragment.newInstance();
                }
                showFragment(niceCommentFragment,
                        "Nice_Comment_Fragment" + newId);

                break;
            case HomeItem.WEEK_NEW_READ:
                if (hotReadNewsFragment == null) {
                    hotReadNewsFragment = HotReadNewsFragment.newInstance();
                }
                showFragment(hotReadNewsFragment,
                        "Hot_ReadNews_Fragment" + newId);
                break;
            case HomeItem.WEEK_NEW_COMMENT:
                if (hotNewsFragment == null) {
                    hotNewsFragment = HotNewsFragment.newInstance();
                }
                showFragment(hotNewsFragment,
                        "Hot_News_Fragment" + newId);
                break;
            default:
                if (otherNewListFragment == null) {
                    otherNewListFragment = OtherNewListFragment.newInstance(newId);
                }
                showFragment(otherNewListFragment,
                        "New_List_Fragment" + newId);
                break;
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl, fragment, tag)
                .commit();
    }
}
