package com.ruzhan.awaker.article.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.News
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import kotlinx.android.synthetic.main.awaker_article_frag_new_all.*

/**
 * Created by ruzhan on 2017/7/6.
 */

class ArticleNewAllFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = ArticleNewAllFragment()
    }

    private lateinit var articleNewAllViewModel: ArticleNewAllViewModel
    private lateinit var articleNewAllAdapter: ArticleNewAllAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_new_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleNewAllViewModel = ViewModelProviders.of(this).get(ArticleNewAllViewModel::class.java)


        articleNewAllAdapter = ArticleNewAllAdapter(object : OnItemClickListener<News> {
            override fun onItemClick(position: Int, bean: News, itemView: View) {
                activity?.let {
                    val url = if (bean.cover_url == null) "" else bean.cover_url.ori
                    ArticleNewDetailActivity.launch(it, bean.id, bean.title, url)
                }
            }
        })

        recycler_view.adapter = articleNewAllAdapter

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {
                return articleNewAllAdapter.getSpanSize(position)
            }
        }
        recycler_view.layoutManager = manager

        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
                OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                articleNewAllViewModel.getNewsAllList(RequestStatus.REFRESH)
            }

            override fun onLoadMore() {
                articleNewAllViewModel.getNewsAllList(RequestStatus.LOAD_MORE)
            }
        })

        articleNewAllViewModel.loadStatusLiveData.observe(this@ArticleNewAllFragment,
                Observer { loadStatus ->
            loadStatus?.let {
                swipe_refresh.isRefreshing = LoadStatus.LOADING == loadStatus
            }
        })

        articleNewAllViewModel.requestStatusLiveData.observe(this@ArticleNewAllFragment,
                Observer { requestStatus ->
            requestStatus?.let {
                when (requestStatus.refreshStatus) {
                    RequestStatus.REFRESH -> articleNewAllAdapter.setRefreshData(requestStatus.data)
                    RequestStatus.LOAD_MORE -> articleNewAllAdapter.setLoadMoreData(requestStatus.data)
                }
            }
        })

        articleNewAllViewModel.loadLocalNews()
        articleNewAllViewModel.getNewsAllList(RequestStatus.REFRESH)
    }
}
