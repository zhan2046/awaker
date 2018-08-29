package com.ruzhan.awaker.article.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Header
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.util.ResUtils
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import kotlinx.android.synthetic.main.awaker_article_frag_new_detail.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailFragment: Fragment() {

    companion object {

        private const val NEW_ID = "newId"
        private const val NEW_TITLE = "newTitle"
        private const val NEW_URL = "newUrl"

        @JvmStatic
        fun newInstance(newId: String, title: String, imageUrl: String): ArticleNewDetailFragment {
            val args = Bundle()
            args.putString(NEW_ID, newId)
            args.putString(NEW_TITLE, title)
            args.putString(NEW_URL, imageUrl)
            val frag = ArticleNewDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var newId: String
    private lateinit var title: String
    private lateinit var imageUrl: String

    private val header: Header = Header()

    private lateinit var articleNewDetailViewModel: ArticleNewDetailViewModel
    private lateinit var articleNewDetailAdapter: ArticleNewDetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_new_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            newId = it.getString(NEW_ID)
            title = it.getString(NEW_TITLE)
            imageUrl = it.getString(NEW_URL)
        }

        comment_right_tv.typeface = FontHelper.get().getLightTypeface()
        comment_et.typeface = FontHelper.get().getLightTypeface()

        toolbar.title = title
        setToolbar(toolbar)

        val commentCountStr = ResUtils.getString(R.string.awaker_article_new_comment_count, "0")
        comment_right_tv.text = commentCountStr

        header.title = title
        header.url = imageUrl

        articleNewDetailAdapter = ArticleNewDetailAdapter()
        articleNewDetailAdapter.setHeaderData(header)
        recycler_view.adapter = articleNewDetailAdapter

        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
                OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                articleNewDetailViewModel.getNewDetail(RequestStatus.REFRESH, newId)
            }

            override fun onLoadMore() {

            }
        })

        articleNewDetailViewModel = ViewModelProviders.of(this)
                .get(ArticleNewDetailViewModel::class.java)
        articleNewDetailViewModel.loadStatusLiveData.observe(this@ArticleNewDetailFragment,
                Observer { loadStatus ->
                    loadStatus?.let {
                        swipe_refresh.isRefreshing = LoadStatus.LOADING == loadStatus
                    }
                })

        articleNewDetailViewModel.requestStatusLiveData.observe(this@ArticleNewDetailFragment,
                Observer { requestStatus ->
                    requestStatus?.let {
                        when (requestStatus.refreshStatus) {
                            RequestStatus.REFRESH ->
                                newDetailToNewEleList(requestStatus.data)
                        }
                    }
                })

        articleNewDetailViewModel.commentsLiveData.observe(this@ArticleNewDetailFragment,
                Observer { commentList ->
                    commentList?.let {
                        articleNewDetailAdapter.setCommentListData(it)
                    }
                })

        articleNewDetailViewModel.newElesLiveData.observe(this@ArticleNewDetailFragment,
                Observer { newEleList ->
                    newEleList?.let {
                        articleNewDetailAdapter.setNewEleData(it)
                    }
                })

        articleNewDetailViewModel.loadLocalNewDetail(newId)
        articleNewDetailViewModel.loadLocalCommentList(newId)

        articleNewDetailViewModel.getNewDetail(RequestStatus.REFRESH, newId)
        articleNewDetailViewModel.getHotCommentList(newId)
    }

    private fun setToolbar(toolbar: Toolbar?) {
        if (toolbar == null) {
            return
        }
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener { activity.onBackPressed() })

        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun newDetailToNewEleList(newDetail: NewDetail) {
        val user = newDetail.user
        user?.let {
            header.userName = user.nickname
            header.userUrl = user.avatar128
        }

        header.title = newDetail.title

        newDetail.cover_url?.let {
            header.url = newDetail.cover_url.ori
        }

        header.createTime = newDetail.create_time
        articleNewDetailAdapter.setHeader(header)

        newDetail.comment?.let {
            val commentCountStr = ResUtils.getString(R.string.awaker_article_new_comment_count,
                    newDetail.comment)
            comment_right_tv.text = commentCountStr
        }

        val html = newDetail.content
        html?.let {
            articleNewDetailViewModel.articleHtmlToModelList(html)
        }
    }
}