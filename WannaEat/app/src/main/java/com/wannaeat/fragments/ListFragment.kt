package com.wannaeat.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.wannaeat.fragments.ListFragment.Companion.DEFAULT_QUERY
import com.wannaeat.fragments.ListFragment.Companion.LAST_SEARCH_QUERY
import kotlinx.android.synthetic.main.fragment_list.*

import com.wannaeat.Injection
import com.wannaeat.MainActivity
import com.wannaeat.R
import com.wannaeat.model.Repo
import com.wannaeat.ui.ReposAdapter
import com.wannaeat.ui.SearchRepositoriesViewModel

import kotlinx.android.synthetic.main.fragment_list.emptyList
import kotlinx.android.synthetic.main.fragment_list.search_repo
import kotlinx.android.synthetic.main.fragment_list.view.*


import kotlin.collections.emptyList

class ListFragment : Fragment() {
    // TODO: Rename and change types of parameter
    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = ReposAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onStart() {
        viewModel = ViewModelProviders.of(this, this.context?.let { Injection.provideViewModelFactory(it) })
                .get(SearchRepositoriesViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)

        initAdapter()

        val query = if (LAST_SEARCH_QUERY.length>0 ) LAST_SEARCH_QUERY else DEFAULT_QUERY

        viewModel.searchRepo(query)
        initSearch(query)
        viewModel.repos
        super.onStart()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initAdapter() {
        this.recyclerView.adapter = adapter
        viewModel.repos.observe(viewLifecycleOwner, Observer<PagedList<Repo>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(this.context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        search_repo.text.trim().let {
            if (it.isNotEmpty()) {
                recyclerView.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                adapter.submitList(null)
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = ""
        private const val DEFAULT_QUERY = "Chicago"
    }


}