package com.future.movie.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.font.FontHelper
import com.future.movie.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_load_more.*

class LoadMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    init {
        load_tv.typeface = FontHelper.get().lightFontTypeface
    }

    fun bind(isShowLoadMore: Boolean) {
        load_progress_bar.visibility = if (isShowLoadMore) View.VISIBLE else View.GONE
        load_tv.text = if (isShowLoadMore) itemView.resources.getString(R.string.load_start)
        else itemView.resources.getString(R.string.load_end)
    }
}