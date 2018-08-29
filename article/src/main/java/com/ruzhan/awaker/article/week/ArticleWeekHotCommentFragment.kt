package com.ruzhan.awaker.article.week

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.OnFragmentLoadListener
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.news.ArticleNewDetailActivity
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import kotlinx.android.synthetic.main.awaker_article_frag_new_hot_read.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleWeekHotCommentFragment : Fragment(), OnFragmentLoadListener {

    companion object {

        @JvmStatic
        fun newInstance() = ArticleWeekHotCommentFragment()
    }

    private lateinit var articleWeekHotReadAdapter: ArticleWeekHotReadAdapter
    private lateinit var articleWeekHotCommentViewModel: ArticleWeekHotCommentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_new_hot_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleWeekHotReadAdapter = ArticleWeekHotReadAdapter(object : OnItemClickListener<News> {
            override fun onItemClick(position: Int, bean: News, itemView: View) {
                activity?.let {
                    val url = if (bean.cover_url == null) "" else bean.cover_url.ori
                    ArticleNewDetailActivity.launch(it, bean.id, bean.title, url)
                }
            }
        })
        val manager = GridLayoutManager(activity, 2)
        recycler_view.layoutManager = manager
        recycler_view.adapter = articleWeekHotReadAdapter

        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
                OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                articleWeekHotCommentViewModel.getWeekHotCommentNews(RequestStatus.REFRESH)
            }

            override fun onLoadMore() {

            }
        })

        articleWeekHotCommentViewModel = ViewModelProviders.of(this).get(ArticleWeekHotCommentViewModel::class.java)

        articleWeekHotCommentViewModel.loadStatusLiveData.observe(this@ArticleWeekHotCommentFragment,
                Observer { loadStatus ->
                    loadStatus?.let {
                        swipe_refresh.isRefreshing = LoadStatus.LOADING == loadStatus
                    }
                })

        articleWeekHotCommentViewModel.requestStatusLiveData.observe(this@ArticleWeekHotCommentFragment,
                Observer { requestStatus ->
                    requestStatus?.let {
                        when (requestStatus.refreshStatus) {
                            RequestStatus.REFRESH -> articleWeekHotReadAdapter.setRefreshData(requestStatus.data)
                        }
                    }
                })
    }

    override fun startLoadData() {
        articleWeekHotCommentViewModel.loadLocalHotCommentList()
        articleWeekHotCommentViewModel.getWeekHotCommentNews(RequestStatus.REFRESH)
    }
}