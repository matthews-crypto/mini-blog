package com.example.mini_blog

data class Article(
    val id: Long,
    val title: String,
    val content: String
) {
    constructor(title: String, content: String) : this(-1, title, content)
}
