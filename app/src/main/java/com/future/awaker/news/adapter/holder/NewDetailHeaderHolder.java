package com.future.awaker.news.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.Header;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.ruzhan.lion.helper.FontHelper;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewDetailHeaderHolder extends RecyclerView.ViewHolder {

    private ItemNewDetailHeaderBinding binding;

    public NewDetailHeaderHolder(ItemNewDetailHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.titleTv.setTypeface(FontHelper.get().getBoldTypeface());
        binding.nameTv.setTypeface(FontHelper.get().getBoldTypeface());
        binding.timeTv.setTypeface(FontHelper.get().getLightTypeface());
    }

    public void bind(Header bean) {
        binding.setHeader(bean);
        binding.executePendingBindings();
    }
}
