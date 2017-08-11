package com.future.awaker.news;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.base.listener.onPageSelectedListener;
import com.future.awaker.data.News;
import com.future.awaker.data.source.AwakerRepository;
import com.future.awaker.databinding.FragNewBinding;
import com.future.awaker.util.LogUtils;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewListFragment extends BaseListFragment<FragNewBinding>
        implements OnItemClickListener<News>, onPageSelectedListener {

    private static final String NEW_ID = "newId";

    private NewViewModel newViewModel;
    private NewListAdapter adapter;
    private boolean isFirst;

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

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        onRefresh();
    }

    @Override
    public void onDestroyView() {
        newViewModel.clear();
        AwakerRepository.get().close();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("NewListFragment onPageSelected");
        if (!isFirst) {
            onRefresh();
            isFirst = true;
        }
    }
}
