package com.future.awaker.news.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailCommentMoreBinding;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailCommentMoreHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailCommentMoreBinding binding;
    private OnItemClickListener<NewEle> listener;

    public NewDetailCommentMoreHolder(ItemNewDetailCommentMoreBinding binding,
                                      OnItemClickListener<NewEle> listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        binding.moreFl.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition(), null);
                }
            }
        });
    }
}
