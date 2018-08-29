package com.ruzhan.awaker.article.comment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.news.ArticleNewDetailActivity
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import kotlinx.android.synthetic.main.awaker_article_frag_comment_list.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleHotCommentFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): ArticleHotCommentFragment {
            return ArticleHotCommentFragment()
        }
    }

    private lateinit var articleHotCommentViewModel: ArticleHotCommentViewModel
    private lateinit var articleHotCommentAdapter: ArticleHotCommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_nice_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleHotCommentAdapter = ArticleHotCommentAdapter(object : OnItemClickListener<Comment> {
            override fun onItemClick(position: Int, bean: Comment, itemView: View) {
                activity?.let {
                    ArticleNewDetailActivity.launch(it, bean.row_id, bean.newstitle.title, "")
                }
            }
        })
        recycler_view.adapter = articleHotCommentAdapter

        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
                OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                articleHotCommentViewModel.getHotCommentList(RequestStatus.REFRESH)
            }

            override fun onLoadMore() {

            }
        })

        articleHotCommentViewModel = ViewModelProviders.of(this).get(ArticleHotCommentViewModel::class.java)
        initLiveData()

        articleHotCommentViewModel.loadLocalCommentList()
        articleHotCommentViewModel.getHotCommentList(RequestStatus.REFRESH)
    }

    private fun initLiveData() {
        articleHotCommentViewModel.loadStatusLiveData.observe(this@ArticleHotCommentFragment,
                Observer { loadStatus ->
                    loadStatus?.let {
                        swipe_refresh.isRefreshing = LoadStatus.LOADING == loadStatus
                    }
                })

        articleHotCommentViewModel.requestStatusLiveData.observe(this@ArticleHotCommentFragment,
                Observer { requestStatus ->
                    requestStatus?.let {
                        when (requestStatus.refreshStatus) {
                            RequestStatus.REFRESH -> articleHotCommentAdapter.setRefreshData(requestStatus.data)
                        }
                    }
                })
    }
}