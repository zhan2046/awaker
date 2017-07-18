package com.future.awaker.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.future.awaker.data.New;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.news.NewListAdapter;
import com.future.awaker.video.SpecialListAdapter;
import com.future.awaker.video.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/15.
 */

public final class DataBindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().load(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageThumbUrl"})
    public static void loadImageThumb(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadThumb(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageCropCircleUrl"})
    public static void loadImageCropCircle(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadCropCircle(iv, imageUrl);
        }
    }

    @BindingAdapter({"news"})
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewListAdapter) {
            ((NewListAdapter) adapter).setData(new ArrayList<>(news));
        }
    }

    @BindingAdapter({"specials"})
    public static void setSpecials(RecyclerView recyclerView, List<Special> specials) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof VideoListAdapter) {
            VideoListAdapter videoListAdapter = (VideoListAdapter) adapter;
            videoListAdapter.setData(new ArrayList<>(specials));
            videoListAdapter.getVideoViewModel().scrollToTop(recyclerView);
        }
    }

    @BindingAdapter({"specialDetail"})
    public static void setSpecialDetail(RecyclerView recyclerView,
                                        SpecialDetail specialDetail) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof SpecialListAdapter) {
            ((SpecialListAdapter)adapter).setSpecialDetail(specialDetail);
        }
    }

}
