package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.model.Article

class NewsAdapter(private var articles: List<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(private val binding: ItemArticleBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Article) {
            binding.textViewTitle.text = news.title
            binding.textViewDescription.text = news.description
            binding.textViewPublishedAt.text = news.publishedAt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return articles.count()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Article {
        return articles[position]
    }
}