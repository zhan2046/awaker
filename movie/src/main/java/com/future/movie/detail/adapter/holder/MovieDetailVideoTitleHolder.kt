package com.future.movie.detail.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruzhan.font.FontHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_detail_video_title.*

class MovieDetailVideoTitleHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    init {
        titleTv.typeface = FontHelper.get().boldFontTypeface
    }
}