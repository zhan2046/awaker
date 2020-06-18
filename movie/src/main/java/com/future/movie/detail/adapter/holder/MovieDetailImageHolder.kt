package com.future.movie.detail.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.imageloader.glide.ImageLoader
import com.future.movie.db.entity.IntroduceItem
import com.future.movie.db.entity.MovieDetailEntity
import com.future.movie.listener.OnItemClickListener
import com.future.movie.model.ImageListModel
import com.future.movie.utils.ViewUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.lion_item_movie_detail_image.*

class MovieDetailImageHolder(itemView: View, private var movieDetail: MovieDetailEntity,
                             listener: OnItemClickListener<ImageListModel>?) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private var imageListModel: ImageListModel = ImageListModel("", 0, "", ArrayList())
    private val imageUrlList = ArrayList<String>()
    private lateinit var url: String

    init {
        if (listener != null) {
            rootCardView.setOnClickListener {
                resetImageListModel(url)
                listener.onItemClick(adapterPosition, imageListModel, it)
            }
        }
    }

    fun bind(bean: IntroduceItem) {
        url = bean.image
        ImageLoader.get().load(picIv, url,
            ViewUtils.getPlaceholder(itemView.context, adapterPosition))
    }

    private fun resetImageListModel(url: String) {
        imageUrlList.clear()
        for (item in movieDetail.introduceList) {
            if (IntroduceItem.IMAGE == item.type) {
                imageUrlList.add(item.image)
            }
        }
        var position = 0
        for (index in imageUrlList.indices) {
            if (url == imageUrlList[index]) {
                position = index
                break
            }
        }
        imageListModel.title = movieDetail.title
        imageListModel.url = url
        imageListModel.position = position
        imageListModel.imageList = imageUrlList
    }
}