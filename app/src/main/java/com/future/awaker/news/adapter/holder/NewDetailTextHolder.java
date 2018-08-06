package com.future.awaker.news.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailTextBinding;
import com.ruzhan.lion.helper.FontHelper;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailTextHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailTextBinding binding;

    public NewDetailTextHolder(ItemNewDetailTextBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.textTv.setTypeface(FontHelper.get().getLightTypeface());
    }

    public void bind(NewEle bean) {
        binding.setNewEle(bean);
        binding.executePendingBindings();
    }
}
