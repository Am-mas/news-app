package com.example.newsapp.ui.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.material.textfield.TextInputLayout
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

    private lateinit var myInputField: TextInputLayout
    private lateinit var buttonLinear: LinearLayout
    private lateinit var myInputInputField: EditText
    private var currentSelectionType: SelectionType? = null

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
        val toolbar = binding.actualActionBarNews
        val customTitle = TextView(requireContext()).apply {
            text = "My Daily Digest"
            setTypeface(null, Typeface.BOLD)
            textSize = 20f
        }

        toolbar.title = "" // Clear default
        toolbar.addView(customTitle)
        val btnSources = binding.sources
        val btnCountry = binding.country
        val btnCategory = binding.category
        val btnSearch = binding.searchQuery
        myInputField = binding.myInputBox
        buttonLinear = binding.buttonLayout
        myInputInputField = binding.myInputField
        myInputInputField.setOnClickListener {
            val options = when (currentSelectionType) {
                SelectionType.CATEGORY -> arrayOf("Business", "Health", "Science")
                SelectionType.COUNTRY -> arrayOf("us", "uk", "ca")
                SelectionType.SOURCE -> arrayOf("bbc-news", "cnn", "techcrunch")
                SelectionType.QUERY -> arrayOf("apple", "tesla")
                else -> emptyArray()
            }

            if (options.isNotEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Choose an option")
                    .setItems(options) { _, which ->
                        val apiKey = "4c0dadb1176a44e9b1770150f95d3482"
                        val selected = options[which]
                        myInputInputField.setText(selected)
                        when (currentSelectionType) {
                            SelectionType.CATEGORY -> viewModel.setCategory(selected, apiKey )
                            SelectionType.COUNTRY -> viewModel.setCountry(selected,apiKey)
                            SelectionType.SOURCE -> viewModel.setSource(selected,apiKey)
                            SelectionType.QUERY -> viewModel.setQuery(selected, apiKey)
                            else -> Unit
                        }

                        // Optional: hide after choosing
                        hideOutlinedTextField()
                    }
                    .show()
            }
        }

        btnSources.setOnClickListener{
            showOutlinedTextField()
            currentSelectionType = SelectionType.SOURCE
        }
        btnCountry.setOnClickListener {
            showOutlinedTextField()
            currentSelectionType = SelectionType.COUNTRY
        }
        btnCategory.setOnClickListener {
            showOutlinedTextField()
            currentSelectionType = SelectionType.CATEGORY
        }
        btnSearch.setOnClickListener {
            showOutlinedTextField()
            currentSelectionType = SelectionType.QUERY
        }
        myInputField.setEndIconOnClickListener {
            hideOutlinedTextField()
        }

        val country = "us"
        val apiKey = "4c0dadb1176a44e9b1770150f95d3482"
        val items = ArrayList<Article>()
        newsAdapter = NewsAdapter(items, parentFragmentManager)
        newsRecycler.layoutManager = LinearLayoutManager(requireContext())
        newsRecycler.adapter = newsAdapter
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

        viewModel.newsArticles.observe(viewLifecycleOwner){ articles ->
            newsAdapter.updateData(articles)
        }

        viewModel.getTopHeadlines(country,apiKey)
    }
    private fun showOutlinedTextField(){
        myInputField.visibility = View.VISIBLE
        buttonLinear.visibility = View.GONE

    }
    private fun hideOutlinedTextField(){
        myInputField.visibility = View.GONE
        buttonLinear.visibility = View.VISIBLE
    }

}

enum class SelectionType {
    CATEGORY, COUNTRY, SOURCE, QUERY
}


