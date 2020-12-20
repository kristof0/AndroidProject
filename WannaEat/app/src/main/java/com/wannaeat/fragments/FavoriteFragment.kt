package com.wannaeat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.wannaeat.Injection
import com.wannaeat.R
import com.wannaeat.model.Repo
import com.wannaeat.ui.ReposAdapter
import com.wannaeat.ui.SearchRepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.emptyList
import kotlin.collections.emptyList


class FavoriteFragment : Fragment() {
    private lateinit var viewModel: SearchRepositoriesViewModel
    private lateinit var adapter: ReposAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =inflater.inflate(R.layout.fragment_favorite, container, false)
        viewModel = ViewModelProviders.of(this, view.context?.let { Injection.provideViewModelFactory(it) })
                .get(SearchRepositoriesViewModel::class.java)
        adapter=ReposAdapter(viewModel)
        return view
    }


    private fun initAdapter() {

        this.recyclerView_fav.adapter = adapter

        viewModel.repoResultFavorite.observe(viewLifecycleOwner, Observer<PagedList<Repo>>{
            Log.d("Activity", "Favorite_list: ${it?.size}")
            showEmptyList(it?.size==0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Toast.makeText(this.context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }





    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList_fav.visibility = View.VISIBLE
            recyclerView_fav.visibility = View.GONE
        } else {
            emptyList_fav.visibility = View.GONE
            recyclerView_fav.visibility = View.VISIBLE
        }
    }
    override fun onStart() {
        val decoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView_fav.addItemDecoration(decoration)
        initAdapter()
        super.onStart()

    }
}