package com.future.awaker.news;

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

    private NewViewModel newViewModel = new NewViewModel();
    private NewListAdapter adapter;

    public static NewListFragment newInstance() {
        return new NewListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new;
    }

    @Override
    protected int getEmptyLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initData() {
        setViewModel(newViewModel);

        binding.setViewmodel(newViewModel);

        adapter = new NewListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        newViewModel.clear();
        super.onDestroyView();
    }

    @Override
    protected void emptyData(boolean isEmpty) {
        adapter.setEmpty(isEmpty);
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        NewDetailActivity.launch(getContext(), bean.id, bean.title);
    }
}
