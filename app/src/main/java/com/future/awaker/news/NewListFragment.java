package com.future.awaker.news;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.FragNewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewListFragment extends BaseListFragment<FragNewBinding> implements OnItemClickListener<New> {

    private NewViewModel newViewModel;
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
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    protected void emptyData(boolean isEmpty) {
        adapter.setEmpty(isEmpty);
    }


    @BindingAdapter({"news"})
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewListAdapter) {
            ((NewListAdapter) adapter).setData(new ArrayList<>(news));
        }
    }

    public void setNewViewModel(NewViewModel newViewModel) {
        this.newViewModel = newViewModel;
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        NewDetailActivity.launch(getContext(), bean.id);
    }
}
