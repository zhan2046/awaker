package com.future.awaker.news.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailImgBinding;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailImgHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailImgBinding binding;

    public NewDetailImgHolder(ItemNewDetailImgBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NewEle bean) {
        binding.setNewEle(bean);
        binding.executePendingBindings();
    }
}
