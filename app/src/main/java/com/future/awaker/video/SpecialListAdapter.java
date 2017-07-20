package com.future.awaker.video;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.News;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.databinding.ItemSpecialListBinding;
import com.future.awaker.databinding.ItemSpecialListHeaderBinding;
import com.future.awaker.video.holder.SpecialListHeaderHolder;
import com.future.awaker.video.holder.SpecialListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListAdapter extends RecyclerView.Adapter {

    private static final String HEADER = "HEADER";

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_ITEM = 1001;

    private SpecialListViewModel viewModel;
    private OnItemClickListener<News> listener;
    public List<Object> dataList = new ArrayList<>();

    public SpecialListAdapter(SpecialListViewModel viewModel,
                              OnItemClickListener<News> listener) {
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void setSpecialDetail(SpecialDetail specialDetail) {
        if (specialDetail == null) {
            return;
        }
        dataList.clear();
        dataList.add(HEADER);
        dataList.addAll(specialDetail.newslist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = dataList.get(position);
        if (object instanceof String) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            ItemSpecialListHeaderBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_special_list_header, parent, false);
            binding.setViewModel(viewModel);
            return new SpecialListHeaderHolder(binding);

        } else {
            ItemSpecialListBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_special_list, parent, false);
            SpecialListHolder holder = new SpecialListHolder(binding);
            binding.setHolder(holder);
            binding.setListener(listener);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_ITEM) {
            ((SpecialListHolder) holder).bind((News) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
