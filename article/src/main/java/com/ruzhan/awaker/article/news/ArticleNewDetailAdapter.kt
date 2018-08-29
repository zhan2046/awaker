package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.Header
import com.ruzhan.awaker.article.model.NewEle
import com.ruzhan.lion.listener.OnItemClickListener
import java.util.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailAdapter(private val imageListener: OnItemClickListener<NewEle>,
                              private val videoListener: OnItemClickListener<NewEle>,
                              private val commentMoreListener: OnItemClickListener<String>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val COMMENT_TITLE = "commentTitle"
        private const val COMMENT_MORE = "commentMore"

        private const val TYPE_HEADER = 1000
        private const val TYPE_TEXT = 1001
        private const val TYPE_IMG = 1002
        private const val TYPE_VIDEO = 1003
        private const val TYPE_COMMENT_TITLE = 1004
        private const val TYPE_COMMENT_ITEM = 1005
        private const val TYPE_COMMENT_MORE = 1006
    }

    private val dataList = ArrayList<Any>()
    private var imageUrlList = ArrayList<String>()

    private var header: Header? = null
    private var newEleList: List<NewEle>? = null
    private var commentList: List<Comment>? = null

    fun setHeader(beanHeader: Header?) {
        header = beanHeader
    }

    fun setHeaderData(beanHeader: Header?) {
        beanHeader?.let {
            header = it
            dataList.clear()

            dataList.add(it)

            newEleList?.let { dataList.addAll(it) }

            dataList.add(COMMENT_TITLE)
            commentList?.let { dataList.addAll(it) }
            dataList.add(COMMENT_MORE)
            notifyDataSetChanged()
        }
    }

    fun setNewEleData(list: List<NewEle>?) {
        list?.let {
            newEleList = it
            dataList.clear()

            imageUrlList = setImageUrlList(newEleList)

            header?.let { dataList.add(it) }

            dataList.addAll(it)

            commentList?.let {
                dataList.add(COMMENT_TITLE)
                dataList.addAll(it)
                dataList.add(COMMENT_MORE)
            }
            notifyDataSetChanged()
        }
    }

    fun setCommentListData(list: List<Comment>?) {
        list?.let {
            commentList = it
            dataList.clear()

            header?.let { dataList.add(it) }

            newEleList?.let {
                dataList.addAll(it)
                dataList.add(COMMENT_TITLE)
                dataList.addAll(it)
                dataList.add(COMMENT_MORE)
            }
            notifyDataSetChanged()
        }
    }

    fun getImageUrlList(): ArrayList<String> {
        return imageUrlList
    }

    private fun setImageUrlList(newEleList: List<NewEle>?): ArrayList<String> {
        imageUrlList.clear()
        if (newEleList != null) {
            for (ele in newEleList) {
                if (NewEle.TYPE_IMG == ele.type) {
                    imageUrlList.add(ele.imgUrl)
                }
            }
        }
        return imageUrlList
    }

    override fun getItemViewType(position: Int): Int {
        val obj = dataList[position]
        if (obj is NewEle) {
            if (NewEle.TYPE_TEXT == obj.type) {
                return TYPE_TEXT
            } else if (NewEle.TYPE_IMG == obj.type) {
                return TYPE_IMG
            } else if (NewEle.TYPE_VIDEO == obj.type) {
                return TYPE_VIDEO
            }
        } else if (obj is String) {
            if (COMMENT_TITLE == obj) {
                return TYPE_COMMENT_TITLE

            } else if (COMMENT_MORE == obj) {
                return TYPE_COMMENT_MORE
            }
        } else if (obj is Comment) {
            return TYPE_COMMENT_ITEM
        } else if (obj is Header) {
            return TYPE_HEADER
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_TEXT -> viewHolder = ArticleNewDetailTextHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_text, parent, false))

            TYPE_IMG -> viewHolder = ArticleNewDetailImgHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_img, parent, false),
                    imageListener)

            TYPE_VIDEO -> viewHolder = ArticleNewDetailVideoHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_video, parent, false),
                    videoListener)

            TYPE_COMMENT_TITLE -> viewHolder = ArticleNewDetailCommentTitleHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_comment_title, parent, false))

            TYPE_COMMENT_ITEM -> viewHolder = ArticleNewCommentHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_comment, parent, false))

            TYPE_COMMENT_MORE -> viewHolder = ArticleCommentMoreHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_comment_more, parent, false),
                    commentMoreListener)

            TYPE_HEADER -> viewHolder = ArticleNewDetailHeaderHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_header, parent, false))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_TEXT -> (holder as ArticleNewDetailTextHolder).bind(dataList[position] as NewEle)
            TYPE_IMG -> (holder as ArticleNewDetailImgHolder).bind(dataList[position] as NewEle)
            TYPE_VIDEO -> {
                val newEle = dataList[position] as NewEle
                header?.let {
                    newEle.imgUrl = it.url
                }
                (holder as ArticleNewDetailVideoHolder).bind(newEle)
            }
            TYPE_COMMENT_ITEM -> (holder as ArticleNewCommentHolder).bind(dataList[position] as Comment)
            TYPE_HEADER -> (holder as ArticleNewDetailHeaderHolder).bind(dataList[position] as Header)
        }
    }

}