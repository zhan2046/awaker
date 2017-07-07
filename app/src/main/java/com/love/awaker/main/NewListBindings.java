package com.love.awaker.main;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.love.awaker.data.New;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class NewListBindings {

    @SuppressWarnings("unchecked")
    //@BindingAdapter("app:news")
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter  instanceof MainAdapter) {
            ((MainAdapter)adapter).setData(news);
        }
    }
}
