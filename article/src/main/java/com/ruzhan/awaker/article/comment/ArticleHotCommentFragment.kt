package com.ruzhan.awaker.article.comment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.news.ArticleNewDetailActivity
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import com.ruzhan.lion.util.AnimUtils
import kotlinx.android.synthetic.main.awaker_article_frag_nice_comment.*

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

        title_tv.typeface = FontHelper.get().getBoldTypeface()
        title_tv.text = resources.getString(R.string.awaker_article_comment_title)
        setToolbar(toolbar)

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

        title_tv.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                title_tv.viewTreeObserver.removeOnGlobalLayoutListener(this)

                title_tv.alpha = 0f
                title_tv.scaleX = 0.8f

                title_tv.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .setDuration(900).interpolator = AnimUtils.getFastOutSlowInInterpolator(activity)
            }
        })
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

    private fun setToolbar(toolbar: Toolbar) {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }
}