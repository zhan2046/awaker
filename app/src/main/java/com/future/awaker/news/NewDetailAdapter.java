package com.future.awaker.news;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.data.Header;
import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.future.awaker.databinding.ItemNewDetailImgBinding;
import com.future.awaker.databinding.ItemNewDetailTextBinding;
import com.future.awaker.databinding.ItemNewDetailVideoBinding;
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

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_TEXT = 1001;
    private static final int TYPE_IMG = 1002;
    private static final int TYPE_VIDEO = 1003;

    private List<Object> dataList = new ArrayList<>();
    private Header header;

    public NewDetailAdapter(Header header) {
        this.header = header;
        dataList.add(header);
    }

    public void setData(List<NewEle> newEleList) {
        if (newEleList == null) {
            return;
        }
        dataList.clear();

        dataList.add(header);
        dataList.addAll(newEleList);
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
        }
        return TYPE_HEADER;
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
            return new NewDetailImgHolder(binding);

        } else if (viewType == TYPE_VIDEO) {
            ItemNewDetailVideoBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_video, parent, false);
            return new NewDetailVideoHolder(binding);

        } else {
            ItemNewDetailHeaderBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_header, parent, false);
            return new NewDetailHeaderHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEXT) {
            ((NewDetailTextHolder)holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_IMG) {
            ((NewDetailImgHolder)holder).bind((NewEle) dataList.get(position));


        } else if (viewType == TYPE_VIDEO) {
            ((NewDetailVideoHolder)holder).bind((NewEle) dataList.get(position));

        } else {
            ((NewDetailHeaderHolder)holder).bind((Header) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


}
