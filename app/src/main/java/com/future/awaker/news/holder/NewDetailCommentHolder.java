package com.future.awaker.news.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.Comment;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailCommentHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailCommentBinding binding;

    public NewDetailCommentHolder(ItemNewDetailCommentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Comment bean) {
        binding.setComment(bean);
        binding.executePendingBindings();
    }
}
