package com.example.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsApp
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragment : Fragment() {
    private var _binding:FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsRecycler: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NewsApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        newsRecycler = binding.recyclerViewNews
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.autoCompleteCountries.setText("us").toString().trim()
        val country = "us"
        val apiKey = "4c0dadb1176a44e9b1770150f95d3482"
        val items = ArrayList<Article>()
        newsAdapter = NewsAdapter(items, parentFragmentManager)
        newsRecycler.layoutManager = LinearLayoutManager(requireContext())
        newsRecycler.adapter = newsAdapter
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)


//        lifecycleScope.launch {
//            viewModel.getTopHeadlines(country, apiKey)
//        }
        viewModel.newsArticles.observe(viewLifecycleOwner){ articles ->
            newsAdapter.updateData(articles)
        }

        viewModel.getTopHeadlines(country,apiKey)
    }

}