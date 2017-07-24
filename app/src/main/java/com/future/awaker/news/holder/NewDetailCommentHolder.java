package com.future.awaker.news.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.future.awaker.R;
import com.future.awaker.data.Comment;
import com.future.awaker.data.User;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.util.ResUtils;

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
        binding.areaTv.setText("( " + bean.area + " )");

        User user = bean.user;
        if (user != null) {
            String userName = TextUtils.isEmpty(user.real_nickname) ?
                    ResUtils.getString(R.string.comment_guest) : user.real_nickname;
            binding.nameTv.setText(userName);

            boolean isGuest = "0".equals(bean.uid);
            binding.nameTv.setTextColor(isGuest ?
                    Color.parseColor("#FF383838") : Color.parseColor("#FFEC6A5C"));
            if (isGuest) {
                binding.iconIv.setImageResource(R.drawable.ic_gongjihui);

            } else {
                ImageLoader.get().loadCropCircle(binding.iconIv, user.avatar64);
            }
        }

        if (!TextUtils.isEmpty(bean.sina_name) && !TextUtils.isEmpty(bean.sina_avatar)) {
            binding.nameTv.setText(bean.sina_name);
            binding.nameTv.setTextColor(Color.parseColor("#FF60C5BA"));
            ImageLoader.get().loadCropCircle(binding.iconIv, bean.sina_avatar);
        }
    }
}
