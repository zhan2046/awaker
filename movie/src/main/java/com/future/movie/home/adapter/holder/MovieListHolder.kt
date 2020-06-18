package com.future.movie.home.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.font.FontHelper
import com.future.imageloader.glide.ImageLoader
import com.future.movie.db.entity.MovieEntity
import com.future.movie.listener.OnItemClickListener
import com.future.movie.utils.ViewUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_list.*

class MovieListHolder(itemView: View, listener: OnItemClickListener<MovieEntity>?) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    private lateinit var movie: MovieEntity

    override val containerView: View?
        get() = itemView

    init {
        val lightFontTypeface = FontHelper.get().lightFontTypeface
        titleTv.typeface = FontHelper.get().boldFontTypeface
        contentTv.typeface = lightFontTypeface
        if (listener != null) {
            rootCardView.setOnClickListener {
                listener.onItemClick(adapterPosition, movie, picIv)
            }
        }
    }

    fun bind(bean: MovieEntity) {
        movie = bean
        titleTv.text = movie.title
        contentTv.text = movie.content
        ImageLoader.get().load(picIv, movie.image,
            ViewUtils.getPlaceholder(itemView.context, adapterPosition))
    }
}