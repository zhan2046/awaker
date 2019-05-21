package com.ruzhan.awaker.article.comment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ruzhan.awaker.article.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awaker_article_container)

        newId = intent.getStringExtra(NEW_ID)

        if (savedInstanceState == null) {
            val frag = ArticleCommentListFragment.newInstance(newId)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, frag, "ArticleCommentListFragment")
                    .commit()
        }
    }
}