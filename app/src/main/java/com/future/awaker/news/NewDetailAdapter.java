package com.future.awaker.news;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.future.awaker.data.NewDetail;
import com.future.awaker.data.NewEle;

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

    public void setData(NewDetail newDetail) {
        if (newDetail == null) {
            return;
        }
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

            return null;
        } else if (viewType == TYPE_IMG) {


            return null;
        } else if (viewType == TYPE_VIDEO) {


            return null;
        } else {


            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEXT) {


        } else if (viewType == TYPE_IMG) {



        } else if (viewType == TYPE_VIDEO) {


        } else {


        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


}
