package com.future.awaker.news.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Comment;
import com.future.awaker.data.User;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.databinding.ItemNewHotCommentBinding;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.util.ResUtils;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class NewHotCommentHolder extends RecyclerView.ViewHolder {

    private ItemNewHotCommentBinding binding;
    private OnItemClickListener<Comment> listener;
    private Comment comment;

    public NewHotCommentHolder(ItemNewHotCommentBinding binding,
                               OnItemClickListener<Comment> listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        binding.rootCard.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition(), comment);
                }
            }
        });
    }

    public void bind(Comment bean) {
        comment = bean;
        binding.contentTv.setText(bean.content);
        String newTitle = bean.newstitle == null ? "" : bean.newstitle.title;
        binding.newTitleTv.setText("@" + newTitle);
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
