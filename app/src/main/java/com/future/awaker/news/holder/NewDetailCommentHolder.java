package com.future.awaker.news.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.future.awaker.R;
import com.future.awaker.data.Comment;
import com.future.awaker.data.User;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.imageloader.ImageLoader;

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
        binding.contentTv.setText(bean.content);
        binding.timeTv.setText(bean.create_time);
        binding.areaTv.setText("( "+ bean.area + " )");

        User user = bean.user;
        if (user != null) {
            binding.nameTv.setText(user.real_nickname);
            boolean isGuest = "0".equals(bean.uid);
            binding.nameTv.setTextColor(isGuest ?
                    Color.parseColor("#FF383838") : Color.parseColor("#FFEC6A5C"));
            if (isGuest) {
                binding.iconIv.setImageResource(R.drawable.ic_gongjihui);

            } else {
                ImageLoader.get().loadCropCircle(binding.iconIv, user.avatar64);
            }
        }
    }
}
