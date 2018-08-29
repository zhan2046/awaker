package com.ruzhan.awaker.article.comment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ruzhan.awaker.article.R

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleCommentListActivity : AppCompatActivity() {

    companion object {

        private const val NEW_ID = "newId"

        @JvmStatic
        fun launch(activity: Activity, newId: String) {
            val intent = Intent(activity, ArticleCommentListActivity::class.java)
            intent.putExtra(NEW_ID, newId)
            activity.startActivity(intent)
        }
    }

    private lateinit var newId: String

    private var articleCommentListFragment: ArticleCommentListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awaker_article_container)

        newId = intent.getStringExtra(NEW_ID)

        if (articleCommentListFragment == null) {
            articleCommentListFragment = ArticleCommentListFragment.newInstance(newId)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, articleCommentListFragment, "ArticleCommentListFragment")
                    .commit()
        }
    }
}