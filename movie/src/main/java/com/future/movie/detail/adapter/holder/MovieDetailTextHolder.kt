package com.future.movie.detail.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruzhan.font.FontHelper
import com.future.movie.db.entity.IntroduceItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_detail_text.*

class MovieDetailTextHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    init {
        textTv.typeface = FontHelper.get().lightFontTypeface
    }

    fun bind(bean: IntroduceItem) {
        textTv.text = bean.text
    }
}