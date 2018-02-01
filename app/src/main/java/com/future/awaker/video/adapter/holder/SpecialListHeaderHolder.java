package com.future.awaker.video.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.databinding.ItemSpecialListHeaderBinding;
import com.future.awaker.imageloader.ImageLoader;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListHeaderHolder extends RecyclerView.ViewHolder {

    private ItemSpecialListHeaderBinding binding;

    public SpecialListHeaderHolder(ItemSpecialListHeaderBinding binding,
                                   String title, String imageUrl) {
        super(binding.getRoot());
        this.binding = binding;

        binding.titleTv.setText(title);
        binding.contentTv.setText("");
        ImageLoader.get().load(binding.imageIv, imageUrl);
    }

    public void bind(String updateTitle, String updateImageUrl, String updateContent) {
        binding.titleTv.setText(updateTitle);
        binding.contentTv.setText(updateContent);
        ImageLoader.get().load(binding.imageIv, updateImageUrl);
    }
}
