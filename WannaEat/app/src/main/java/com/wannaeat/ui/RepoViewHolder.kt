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
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wannaeat.Injection
import com.wannaeat.R
import com.wannaeat.model.Repo


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View, private val viewModel: SearchRepositoriesViewModel) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.repo_name)
    private val address: TextView = view.findViewById(R.id.repo_address)
    private val imageView: ImageView = view.findViewById(R.id.repo_imageView)
    private val price: TextView = view.findViewById(R.id.repo_price)
    private val favCheck: CheckBox = view.findViewById(R.id.repo_favCheck)


    private var repo: Repo? = null

    init {


        view.setOnClickListener {
            SearchRepositoriesViewModel.selectedRepo = repo
            Navigation.findNavController(view).navigate(R.id.detailFragment)
        }

    }

    fun bind(repo: Repo?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)


        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: Repo) {

        this.repo = repo
        name.text = repo.name


        // set address
        var addressVisibility = View.GONE
        if (repo.address != null) {
            address.text = repo.address
            addressVisibility = View.VISIBLE
        }
        address.visibility = addressVisibility


        // set name
        var nameVisibility = View.GONE
        if (!repo.name.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            name.text = repo.name
            nameVisibility = View.VISIBLE
        }
        name.visibility = nameVisibility


        //set price
        var priceVisibility = View.GONE
        if (!repo.price.toString().isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            price.text = resources.getString(R.string.price, repo.price.toString())
            priceVisibility = View.VISIBLE
        }
        price.visibility = priceVisibility

        //star
        favCheck.isChecked = repo.favorite == 1


        //inserting image
        Glide.with(this.itemView)
                .load(repo.image_url)
                .into(imageView)

    }

    companion object {
        fun create(parent: ViewGroup, viewModel: SearchRepositoriesViewModel): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)

            return RepoViewHolder(view, viewModel)
        }
    }
}
