package com.future.awaker.news;

import android.os.Bundle;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.FragNewBinding;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewListFragment extends BaseListFragment<FragNewBinding>
        implements OnItemClickListener<New> {

    private static final String NEW_ID = "newId";

    private NewViewModel newViewModel;
    private NewListAdapter adapter;

    public static NewListFragment newInstance(int newId) {
        Bundle args = new Bundle();
        args.putInt(NEW_ID, newId);
        NewListFragment fragment = new NewListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new;
    }

    @Override
    protected void initData() {
        int newId = getArguments().getInt(NEW_ID, 0);
        newViewModel = new NewViewModel(newId);
        setListViewModel(newViewModel);

        binding.setViewModel(newViewModel);

        adapter = new NewListAdapter(newViewModel, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        newViewModel.clear();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }
}
