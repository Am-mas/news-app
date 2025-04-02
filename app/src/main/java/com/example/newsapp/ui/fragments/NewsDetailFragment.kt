package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsDetailBinding
import com.example.newsapp.model.Article
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewsDetailFragment : BottomSheetDialogFragment() {
    private var news: Article? = null

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news = it.getParcelable("news")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news?.let {
            binding.textViewTitle.text = it.title
            binding.textViewAuthor.text = it.author
            binding.textViewPublishedAt.text = it.publishedAt
            binding.textViewContent.text = it.content
            binding.imageViewNews.load(it.urlToImage) {
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
                crossfade(true)
            }

        }


    }

    companion object {
        fun newInstance(news: Article): NewsDetailFragment{
            return NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("news", news)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}