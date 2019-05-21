package com.ruzhan.awaker.article.news

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
import com.ruzhan.awaker.article.comment.ArticleCommentListActivity
import com.ruzhan.awaker.article.model.Header
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.NewEle
import com.ruzhan.awaker.article.util.HtmlParser
import com.ruzhan.awaker.article.util.KeyboardUtils
import com.ruzhan.awaker.article.util.ResUtils
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.helper.OnRefreshHelper
import com.ruzhan.lion.listener.OnItemClickListener
import com.ruzhan.lion.model.LoadStatus
import com.ruzhan.lion.model.RequestStatus
import com.ruzhan.lion.rx.Subscriber
import com.ruzhan.movie.ImageListModel
import com.ruzhan.movie.detail.ImageDetailActivity
import com.ruzhan.movie.video.WebVideoActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.awaker_article_frag_new_detail.*

class ArticleNewDetailFragment : Fragment() {

    companion object {

        private const val NEW_ID = "newId"
        private const val NEW_TITLE = "newTitle"
        private const val NEW_URL = "newUrl"

        private const val RESET_EDIT_VALUE = 30

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

        initRecyclerView()

        articleNewDetailViewModel = ViewModelProviders.of(this)
                .get(ArticleNewDetailViewModel::class.java)

        initLiveData()
        initListener()

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

    private fun initRecyclerView() {
        articleNewDetailAdapter = ArticleNewDetailAdapter(
                object : OnItemClickListener<NewEle> { // image

                    override fun onItemClick(position: Int, bean: NewEle, itemView: View) {
                        val imageUrlList = articleNewDetailAdapter.getImageUrlList()
                        val imageUrl = bean.imgUrl
                        val imageListModel = ImageListModel(title, imageUrlList.indexOf(imageUrl),
                                imageUrl, imageUrlList)

                        activity?.let {
                            ImageDetailActivity.launch(it, imageListModel)
                        }
                    }
                },
                object : OnItemClickListener<NewEle> { // video

                    override fun onItemClick(position: Int, bean: NewEle, itemView: View) {
                        Flowable.create<String>({ e ->
                            val playUrl = HtmlParser.htmlToVideoUrl(bean.html)
                            e.onNext(playUrl)
                            e.onComplete()

                        }, BackpressureStrategy.LATEST)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnError(Throwable::printStackTrace)
                                .doOnNext { url ->
                                    activity?.let {
                                        WebVideoActivity.launch(it, url)
                                    }
                                }
                                .doOnComplete { }
                                .subscribe(Subscriber.create())
                    }
                },
                object : OnItemClickListener<String> { // comment more

                    override fun onItemClick(position: Int, bean: String, itemView: View) {
                        activity?.let {
                            ArticleCommentListActivity.launch(it, newId)
                        }
                    }
                })

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
    }

    private fun initLiveData() {
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

        articleNewDetailViewModel.sendCommentLiveData.observe(this@ArticleNewDetailFragment,
                Observer { comment ->
                    comment?.let {
                        val str = resources.getString(R.string.awaker_article_comment_suc)
                        Toast.makeText(context, str + "", Toast.LENGTH_LONG).show()
                        comment_et.setText("")
                        comment_et.isFocusable = false
                        comment_et.isFocusableInTouchMode = true

                        activity?.let { KeyboardUtils.closeSoftInput(it, comment_et) }
                        articleNewDetailViewModel.getHotCommentList(newId)
                    }
                })
    }

    private fun initListener() {
        send_iv.setOnClickListener(View.OnClickListener {
            val content = comment_et.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                return@OnClickListener
            }
            articleNewDetailViewModel.sendNewsComment(newId, content, "", "")
        })

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

        comment_right_tv.setOnClickListener {
            activity?.let { ArticleCommentListActivity.launch(it, newId) }
        }

        comment_et.setOnFocusChangeListener { _, hasFocus ->
            send_iv.visibility = if (hasFocus) View.VISIBLE else View.GONE
            comment_right_tv.visibility = if (hasFocus) View.GONE else View.VISIBLE
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