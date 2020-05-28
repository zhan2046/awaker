package com.future.movie.detail.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.font.FontHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_detail_title.*

class MovieDetailTitleHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    init {
        tag_tv.typeface = FontHelper.get().lightFontTypeface
        title_tv.typeface = FontHelper.get().boldFontTypeface
    }

    fun bind(title: String, tag: String) {
        title_tv.text = title
        tag_tv.text = tag
    }
}