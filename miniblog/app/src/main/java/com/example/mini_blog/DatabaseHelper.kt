package com.example.mini_blog

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "blog.db"
        private const val TABLE_ARTICLES = "articles"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createQuery = ("CREATE TABLE $TABLE_ARTICLES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TITLE TEXT, "
                + "$COLUMN_CONTENT TEXT)"
                )
        db.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTICLES")
        onCreate(db)
    }

    fun addArticle(article: Article) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, article.title)
        values.put(COLUMN_CONTENT, article.content)

        val db = this.writableDatabase
        db.insert(TABLE_ARTICLES, null, values)
        db.close()
    }

    fun getAllArticles(): List<Article> {
        val articleList = ArrayList<Article>()
        val selectQuery = "SELECT * FROM $TABLE_ARTICLES ORDER BY $COLUMN_ID DESC"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                    val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                    val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                    val article = Article(id, title, content)
                    articleList.add(article)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        db.close()
        return articleList
    }

    fun getArticle(id: Long): Article {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_ARTICLES WHERE $COLUMN_ID = $id"
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        var article = Article(-1, "", "")
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                article = Article(id, title, content)
            }
            cursor.close()
        }
        db.close()
        return article
    }

    fun updateArticle(article: Article) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, article.title)
        values.put(COLUMN_CONTENT, article.content)

        val db = this.writableDatabase
        db.update(
            TABLE_ARTICLES, values, "$COLUMN_ID = ?",
            arrayOf(article.id.toString())
        )
        db.close()
    }

    fun deleteArticle(article: Article) {
        val db = this.writableDatabase
        db.delete(
            TABLE_ARTICLES, "$COLUMN_ID = ?",
            arrayOf(article.id.toString())
        )
        db.close()
    }
}
