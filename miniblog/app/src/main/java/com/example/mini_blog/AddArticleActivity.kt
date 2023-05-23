package com.example.mini_blog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddArticleActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var addButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        addButton = findViewById(R.id.addButton)

        databaseHelper = DatabaseHelper(this)

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            databaseHelper.addArticle(Article(title, content))

            finish()
        }
    }
}
