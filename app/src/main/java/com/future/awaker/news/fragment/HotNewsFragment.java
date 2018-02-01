package com.future.awaker.news.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.News;
import com.future.awaker.databinding.FragNewHotBinding;
import com.future.awaker.news.NewDetailActivity;
import com.future.awaker.news.adapter.NewListAdapter;
import com.future.awaker.news.viewmodel.HotNewsViewModel;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class HotNewsFragment extends BaseListFragment<FragNewHotBinding>
        implements OnItemClickListener<News> {

    private HotNewsViewModel viewModel;
    private NewListAdapter adapter;

    public static HotNewsFragment newInstance() {
        return new HotNewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_hot;
    }

    @Override
    protected void initData() {
        viewModel = new HotNewsViewModel();
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        adapter = new NewListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        viewModel.getHotNewsListLiveData().observe(this, refreshListModel -> {
            if (refreshListModel != null) {
                if (refreshListModel.isRefreshType()) {
                    adapter.setRefreshData(refreshListModel.list);

                } else if (refreshListModel.isUpdateType()) {
                    adapter.setUpdateData(refreshListModel.list);

                }
            }
        });


        viewModel.initLocalHotList();
        onRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }
}
