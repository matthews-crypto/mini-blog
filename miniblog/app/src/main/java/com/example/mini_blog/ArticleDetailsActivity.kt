package com.example.mini_blog

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        titleTextView = findViewById(R.id.titleTextView)
        contentTextView = findViewById(R.id.contentTextView)

        val articleId = intent.getLongExtra("articleId", -1)
        article = DatabaseHelper(this).getArticle(articleId)

        titleTextView.text = article.title
        contentTextView.text = article.content
    }
}
