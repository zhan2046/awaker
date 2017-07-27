package com.future.awaker.news;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.News;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.databinding.FragNewHotReadBinding;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class HotReadNewsFragment extends BaseListFragment<FragNewHotReadBinding>
        implements OnItemClickListener<News> {

    private HotReadViewModel viewModel;
    private NewListAdapter adapter;

    public static HotReadNewsFragment newInstance() {
        return new HotReadNewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_hot_read;
    }

    @Override
    protected void initData() {
        viewModel = new HotReadViewModel();
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        adapter = new NewListAdapter(viewModel, this);
        binding.recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        onRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroyView() {
        viewModel.clear();
        NewRepository.get().close();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, News bean) {
        String url = bean.cover_url == null ? "" : bean.cover_url.ori;
        NewDetailActivity.launch(getContext(), bean.id, bean.title, url);
    }
}
