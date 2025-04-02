package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.fragments.NewsDetailFragment
import javax.inject.Inject

class NewsAdapter(
    private var articles: List<Article>,
    private val fragmentManager: FragmentManager
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(private val binding: ItemArticleBinding):RecyclerView.ViewHolder(binding.root) {
        private val cardLayout = binding.cardLayout
        fun bind(news: Article,fragmentManager: FragmentManager) {
            binding.textViewTitle.text = news.title
            binding.textViewDescription.text = news.description
            binding.textViewPublishedAt.text = news.publishedAt
            binding.imageViewThumbnail.load(news.urlToImage) {
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
                crossfade(true)
            }
            cardLayout.setOnClickListener {
                val bottomSheetDialog = NewsDetailFragment.newInstance(news)
                bottomSheetDialog.show(fragmentManager, "NewsDetailFragment")

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position],fragmentManager)
    }

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged() // Notify RecyclerView to refresh
    }

}