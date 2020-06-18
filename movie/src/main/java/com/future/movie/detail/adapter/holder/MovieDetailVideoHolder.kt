package com.future.movie.detail.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.font.FontHelper
import com.future.imageloader.glide.ImageLoader
import com.future.movie.db.entity.VideoItem
import com.future.movie.listener.OnItemClickListener
import com.future.movie.utils.ViewUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_detail_video.*

class MovieDetailVideoHolder(itemView: View, listener: OnItemClickListener<VideoItem>?) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var videoItem: VideoItem

    init {
        val lightFontTypeface = FontHelper.get().lightFontTypeface
        titleTv.typeface = lightFontTypeface
        if (listener != null) {
            rootCardView.setOnClickListener { listener.onItemClick(adapterPosition, videoItem, it) }
        }
    }

    fun bind(bean: VideoItem) {
        videoItem = bean
        titleTv.text = bean.title
        ImageLoader.get().load(imageIv, bean.image,
            ViewUtils.getPlaceholder(itemView.context, adapterPosition))
    }
}