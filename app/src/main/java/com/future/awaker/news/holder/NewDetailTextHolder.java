package com.future.awaker.news.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailTextBinding;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailTextHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailTextBinding binding;

    public NewDetailTextHolder(ItemNewDetailTextBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NewEle bean) {
        binding.setNewEle(bean);
        binding.executePendingBindings();
    }
}
