package com.future.awaker.news;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Comment;
import com.future.awaker.data.Header;
import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.databinding.ItemNewDetailCommentMoreBinding;
import com.future.awaker.databinding.ItemNewDetailCommentTitleBinding;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.future.awaker.databinding.ItemNewDetailImgBinding;
import com.future.awaker.databinding.ItemNewDetailTextBinding;
import com.future.awaker.databinding.ItemNewDetailVideoBinding;
import com.future.awaker.news.holder.NewDetailCommentHolder;
import com.future.awaker.news.holder.NewDetailCommentMoreHolder;
import com.future.awaker.news.holder.NewDetailHeaderHolder;
import com.future.awaker.news.holder.NewDetailImgHolder;
import com.future.awaker.news.holder.NewDetailTextHolder;
import com.future.awaker.news.holder.NewDetailVideoHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailAdapter extends RecyclerView.Adapter {

    private static final String COMMENT_TITLE = "commentTitle";
    private static final String COMMENT_MORE = "commentMore";

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_TEXT = 1001;
    private static final int TYPE_IMG = 1002;
    private static final int TYPE_VIDEO = 1003;
    private static final int TYPE_COMMENT_TITLE = 1004;
    private static final int TYPE_COMMENT_ITEM = 1005;
    private static final int TYPE_COMMENT_MORE = 1006;

    private List<Object> dataList = new ArrayList<>();
    private OnItemClickListener<NewEle> listener;
    private Header header;
    private List<NewEle> newEleList;
    private List<Comment> commentList;

    private List<NewDetailVideoHolder> videoHolders = new ArrayList<>();

    public NewDetailAdapter(Header header, OnItemClickListener<NewEle> listener) {
        this.header = header;
        this.listener = listener;
        dataList.add(header);
    }

    public void setData(List<NewEle> list) {
        if (list == null) {
            return;
        }
        dataList.clear();
        newEleList = list;

        dataList.add(header);
        dataList.addAll(newEleList);
        if (commentList != null) {
            dataList.add(COMMENT_TITLE);
            dataList.addAll(commentList);
            dataList.add(COMMENT_MORE);
        }
        notifyDataSetChanged();
    }

    public void setCommentList(List<Comment> list) {
        if (list == null) {
            return;
        }
        dataList.clear();
        commentList = list;

        dataList.add(header);
        if (newEleList != null) {
            dataList.addAll(newEleList);
        }
        dataList.add(COMMENT_TITLE);
        dataList.addAll(commentList);
        dataList.add(COMMENT_MORE);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = dataList.get(position);
        if (object instanceof NewEle) {
            NewEle newEle = (NewEle) object;
            if (NewEle.TYPE_TEXT == newEle.type) {
                return TYPE_TEXT;
            } else if (NewEle.TYPE_IMG == newEle.type) {
                return TYPE_IMG;
            } else if (NewEle.TYPE_VIDEO == newEle.type) {
                return TYPE_VIDEO;
            }
        } else if (object instanceof String) {
            String str = (String) object;
            if (COMMENT_TITLE.equals(str)) {
                return TYPE_COMMENT_TITLE;

            } else if (COMMENT_MORE.equals(str)) {
                return TYPE_COMMENT_MORE;
            }
        } else if (object instanceof Comment) {
            return TYPE_COMMENT_ITEM;
        } else if (object instanceof Header) {
            return TYPE_HEADER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            ItemNewDetailTextBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_text, parent, false);
            return new NewDetailTextHolder(binding);

        } else if (viewType == TYPE_IMG) {
            ItemNewDetailImgBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_img, parent, false);
            NewDetailImgHolder holder = new NewDetailImgHolder(binding);
            ;
            binding.setListener(listener);
            binding.setHolder(holder);
            return holder;

        } else if (viewType == TYPE_VIDEO) {
            ItemNewDetailVideoBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_video, parent, false);
            NewDetailVideoHolder holder = new NewDetailVideoHolder(binding, listener);
            binding.setListener(listener);
            binding.setHolder(holder);

            videoHolders.add(holder);
            return holder;

        } else if (viewType == TYPE_COMMENT_TITLE) {
            ItemNewDetailCommentTitleBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment_title, parent, false);
            return new EmptyHolder(binding);

        } else if (viewType == TYPE_COMMENT_ITEM) {
            ItemNewDetailCommentBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment, parent, false);
            return new NewDetailCommentHolder(binding);

        } else if (viewType == TYPE_COMMENT_MORE) {
            ItemNewDetailCommentMoreBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment_more, parent, false);

            return new NewDetailCommentMoreHolder(binding, listener);

        } else if (viewType == TYPE_HEADER) {
            ItemNewDetailHeaderBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_header, parent, false);
            return new NewDetailHeaderHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEXT) {
            ((NewDetailTextHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_IMG) {
            ((NewDetailImgHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_VIDEO) {
            ((NewDetailVideoHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_COMMENT_ITEM) {
            ((NewDetailCommentHolder) holder).bind((Comment) dataList.get(position));

        } else if (viewType == TYPE_HEADER) {
            ((NewDetailHeaderHolder) holder).bind((Header) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void onResume() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    public void onPause() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    public void onDestroy() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof NewDetailVideoHolder) {
            NewDetailVideoHolder videoHolder = (NewDetailVideoHolder) holder;
            videoHolder.mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof NewDetailVideoHolder) {
            NewDetailVideoHolder videoHolder = (NewDetailVideoHolder) holder;
            videoHolder.mAgentWeb.getWebLifeCycle().onResume();
        }
    }
}
