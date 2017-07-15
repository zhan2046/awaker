package com.future.awaker.datadinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.future.awaker.data.SpecialDetail;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.video.SpecialListAdapter;

/**
 * Created by ruzhan on 2017/7/15.
 */

public final class AdapterDataBinding {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().load(iv, imageUrl);
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
