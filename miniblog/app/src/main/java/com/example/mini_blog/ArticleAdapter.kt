package com.example.mini_blog


    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

    class ArticleAdapter(
        private var articles: List<Article>,
        private val articleClickListener: ArticleClickListener
    ) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_article_details, parent, false)
            return ArticleViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val currentArticle = articles[position]
            holder.bind(currentArticle)
        }

        override fun getItemCount(): Int {
            return articles.size
        }

        fun updateArticles(newArticles: List<Article>) {
            articles = newArticles
            notifyDataSetChanged()
        }

        inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(article: Article) {
                titleTextView.text = article.title
                contentTextView.text = article.content
            }

            override fun onClick(view: View) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedArticle = articles[position]
                    articleClickListener.onArticleClick(clickedArticle)
                }
            }
        }

        interface ArticleClickListener {
            fun onArticleClick(article: Article)
        }
    }


