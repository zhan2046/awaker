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

public class NewFragment extends BaseListFragment<FragNewBinding> implements OnItemClickListener<New> {

    private NewViewModel newViewModel;
    private NewAdapter adapter;

    public static NewFragment newInstance() {
        return new NewFragment();
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

        adapter = new NewAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        newViewModel.setToken("f32b30c2a289bfca2c9857ffc5871ac8", 0);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }


    @BindingAdapter({"news"})
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewAdapter) {
            ((NewAdapter)adapter).setData(new ArrayList<>(news));
        }
    }

    public void setNewViewModel(NewViewModel newViewModel) {
        this.newViewModel = newViewModel;
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        Toast.makeText(getContext(), position + " : " + bean.title, Toast.LENGTH_SHORT).show();
    }
}
