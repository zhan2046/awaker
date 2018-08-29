package com.ruzhan.awaker.article.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ruzhan.awaker.article.R

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailActivity: AppCompatActivity() {

    companion object {

        private const val NEW_ID = "newId"
        private const val NEW_TITLE = "newTitle"
        private const val NEW_URL = "newUrl"

        @JvmStatic
        fun launch(activity: Activity, newId: String, title: String, imageUrl: String) {
            val intent = Intent(activity, ArticleNewDetailActivity::class.java)
            intent.putExtra(NEW_ID, newId)
            intent.putExtra(NEW_TITLE, title)
            intent.putExtra(NEW_URL, imageUrl)
            activity.startActivity(intent)
        }
    }

    private lateinit var newId: String
    private lateinit var title: String
    private lateinit var imageUrl: String

    private var articleNewDetailFragment: ArticleNewDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awaker_article_container)

        newId = intent.getStringExtra(NEW_ID)
        title = intent.getStringExtra(NEW_TITLE)
        imageUrl = intent.getStringExtra(NEW_URL)

        if (articleNewDetailFragment == null) {
            articleNewDetailFragment = ArticleNewDetailFragment.newInstance(newId, title, imageUrl)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, articleNewDetailFragment, "ArticleNewDetailFragment")
                    .commit()
        }
    }
}