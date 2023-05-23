package com.example.mini_blog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ArticleAdapter.ArticleClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseHelper = DatabaseHelper(this)

        articleAdapter = ArticleAdapter(databaseHelper.getAllArticles(), this)
        recyclerView.adapter = articleAdapter

        val addArticleButton = findViewById<Button>(R.id.addArticleButton)
        addArticleButton.setOnClickListener {
            val intent = Intent(this, AddArticleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.updateArticles(databaseHelper.getAllArticles())
    }

    override fun onArticleClick(article: Article) {
        val intent = Intent(this, ArticleDetailsActivity::class.java)
        intent.putExtra("articleId", article.id)
        startActivity(intent)
    }
}
