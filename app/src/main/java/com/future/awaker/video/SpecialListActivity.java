package com.future.awaker.video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.future.awaker.R;
import com.future.awaker.base.BaseActivity;
import com.future.awaker.databinding.ActivitySpecialListBinding;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListActivity extends BaseActivity {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";

    private ActivitySpecialListBinding binding;
    private SpecialListFragment specialListFragment;

    public static void launch(Context context, String id, String title, String url) {
        Intent intent = new Intent(context, SpecialListActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_special_list);

        String id = getIntent().getStringExtra(ID);
        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);

        if (TextUtils.isEmpty(id)) {
            finish();
        }

        if (specialListFragment == null) {
            specialListFragment = SpecialListFragment.newInstance(id, title, url);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fl, specialListFragment,
                            SpecialListFragment.class.getSimpleName())
                    .commit();
        }
    }
}
