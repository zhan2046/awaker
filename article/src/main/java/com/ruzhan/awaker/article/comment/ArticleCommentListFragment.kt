package com.ruzhan.awaker.article.comment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.util.KeyboardUtils
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import kotlinx.android.synthetic.main.awaker_article_frag_comment_list.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleCommentListFragment : Fragment() {

    companion object {

        private const val NEW_ID = "newId"
        private const val RESET_EDIT_VALUE = 30

        @JvmStatic
        fun newInstance(newId: String): ArticleCommentListFragment {
            val args = Bundle()
            args.putString(NEW_ID, newId)
            val frag = ArticleCommentListFragment()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var newId: String

    private lateinit var articleCommentListViewModel: ArticleCommentListViewModel
    private lateinit var articleCommentListAdapter: ArticleCommentListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_comment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            newId = it.getString(NEW_ID)
        }

        comment_et.typeface = FontHelper.get().getLightTypeface()
        toolbar.setTitle(R.string.awaker_article_up_comment_more)
        setToolbar(toolbar)

        articleCommentListAdapter = ArticleCommentListAdapter()
        recycler_view.adapter = articleCommentListAdapter

        articleCommentListViewModel = ViewModelProviders.of(this).get(ArticleCommentListViewModel::class.java)
        initLiveData()
        initListener()

        articleCommentListViewModel.loadLocalCommentList(newId)
        articleCommentListViewModel.getCommentList(RequestStatus.REFRESH, newId)
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

    private fun initLiveData() {
        articleCommentListViewModel.loadStatusLiveData.observe(this@ArticleCommentListFragment,
                Observer { loadStatus ->
                    loadStatus?.let {
                        swipe_refresh.isRefreshing = LoadStatus.LOADING == loadStatus
                    }
                })

        articleCommentListViewModel.requestStatusLiveData.observe(this@ArticleCommentListFragment,
                Observer { requestStatus ->
                    requestStatus?.let {
                        when (requestStatus.refreshStatus) {
                            RequestStatus.REFRESH -> articleCommentListAdapter.setRefreshData(requestStatus.data)
                            RequestStatus.LOAD_MORE -> articleCommentListAdapter.setLoadMoreData(requestStatus.data)
                        }
                    }
                })

        articleCommentListViewModel.emptyDataLiveData.observe(this@ArticleCommentListFragment,
                Observer { result ->
                    result?.let {
                        articleCommentListAdapter.setLoadMore(false)
                    }
                })

        articleCommentListViewModel.sendCommentLiveData.observe(this@ArticleCommentListFragment,
                Observer { comment ->
                    comment?.let {
                        val str = resources.getString(R.string.awaker_article_comment_suc)
                        Toast.makeText(context, str + "", Toast.LENGTH_LONG).show()
                        comment_et.setText("")
                        comment_et.isFocusable = false
                        comment_et.isFocusableInTouchMode = true

                        activity?.let { KeyboardUtils.closeSoftInput(it, comment_et) }
                        articleCommentListViewModel.getCommentList(RequestStatus.REFRESH, newId)
                    }
                })
    }

    private fun initListener() {
        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
                OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                articleCommentListViewModel.getCommentList(RequestStatus.REFRESH, newId)
            }

            override fun onLoadMore() {
                articleCommentListViewModel.getCommentList(RequestStatus.LOAD_MORE, newId)
            }
        })

        send_iv.setOnClickListener {
            val content = comment_et.text.toString().trim()
            if (!TextUtils.isEmpty(content)) {
                articleCommentListViewModel.sendNewsComment(newId, content, "", "")
            }
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (Math.abs(dy) > RESET_EDIT_VALUE && comment_et.isFocusable) {
                    comment_et.setText("")
                    comment_et.isFocusable = false
                    comment_et.isFocusableInTouchMode = true
                    activity?.let { KeyboardUtils.closeSoftInput(it, comment_et) }
                }
            }
        })

        comment_et.setOnClickListener {
            comment_et.isFocusable = true
            comment_et.requestFocus()
        }
    }
}