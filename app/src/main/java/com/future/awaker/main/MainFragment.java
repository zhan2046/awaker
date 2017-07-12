package com.future.awaker.main;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.FragMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class MainFragment extends BaseListFragment<FragMainBinding> implements OnItemClickListener<New> {

    private MainViewModel mainViewModel;
    private MainAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_main;
    }

    @Override
    protected int getEmptyLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initData() {
        setBaseListViewModel(mainViewModel);
        binding.setViewmodel(mainViewModel);

        adapter = new MainAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        mainViewModel.setToken("f32b30c2a289bfca2c9857ffc5871ac8", 0);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }


    @BindingAdapter({"news"})
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof MainAdapter) {
            ((MainAdapter)adapter).setData(new ArrayList<>(news));
        }
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void onItemClick(View view, int position, New bean) {
        Toast.makeText(getContext(), position + " : " + bean.title, Toast.LENGTH_SHORT).show();
    }
}
