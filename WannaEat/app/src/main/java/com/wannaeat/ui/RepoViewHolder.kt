/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wannaeat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannaeat.R
import com.wannaeat.model.Repo


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.repo_name)
    private val address: TextView = view.findViewById(R.id.repo_address)
    private val imageView:ImageView=view.findViewById(R.id.repo_imageView)
    private val price: TextView = view.findViewById(R.id.repo_price)
    private val favCheck: CheckBox = view.findViewById(R.id.repo_favCheck)

    private lateinit var viewModel: SearchRepositoriesViewModel

    private var repo: Repo? = null

    init {
        //viewModel=ViewModelProviders.of(Fragment(), view.context?.let { Injection.provideViewModelFactory(it) }).get(SearchRepositoriesViewModel::class.java)
        view.setOnClickListener {
            SearchRepositoriesViewModel.selectedRepo=repo
            Navigation.findNavController(view).navigate(R.id.navToDetails)
        }
    }

    fun bind(repo: Repo?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            //description.visibility = View.GONE
            //language.visibility = View.GONE

        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo) {

       this.repo = repo
        name.text = repo.name

        // if the description is missing, hide the TextView
        var addressVisibility = View.GONE
        if (repo.address != null) {
            address.text = repo.address
            addressVisibility = View.VISIBLE
        }
        address.visibility = addressVisibility


        // if the language is missing, hide the label and the value
        var nameVisibility = View.GONE
        if (!repo.name.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            name.text = repo.name
            nameVisibility = View.VISIBLE
        }
        name.visibility = nameVisibility

        var priceVisibility = View.GONE
        if (!repo.price.toString().isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            price.text = resources.getString(R.string.price, repo.price.toString())
            priceVisibility = View.VISIBLE
        }
        price.visibility = priceVisibility

        Glide.with(this.itemView)
                .load(repo.image_url)
                .into(imageView)

    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
