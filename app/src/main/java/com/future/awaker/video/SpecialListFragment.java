package com.future.awaker.video;

import android.os.Bundle;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.FragSpecialListBinding;
import com.future.awaker.news.NewDetailActivity;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListFragment extends BaseListFragment<FragSpecialListBinding> implements OnItemClickListener<New> {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";

    private SpecialListViewModel viewModel = new SpecialListViewModel();
    private String url;

    public static SpecialListFragment newInstance(String id, String title, String url) {
        Bundle args = new Bundle();
        SpecialListFragment fragment = new SpecialListFragment();
        args.putString(ID, id);
        args.putString(TITLE, title);
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_special_list;
    }

    @Override
    protected void initData() {
        String id = getArguments().getString(ID);
        String title = getArguments().getString(TITLE);
        url = getArguments().getString(URL);

        viewModel.setParams(id, title, url);

        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        setToolbar(binding.toolbar);

        SpecialListAdapter adapter = new SpecialListAdapter(viewModel, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        NewDetailActivity.launch(getActivity(), bean.id, bean.title, url);
    }
}
