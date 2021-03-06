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

package com.wannaeat.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wannaeat.api.OpenTableService
import com.wannaeat.db.OpenTableLocalCache
import com.wannaeat.model.Repo
import com.wannaeat.model.RepoSearchResult

/**
 * Repository class that works with local and remote data sources.
 */
class OpenTableRepository (
        private val service: OpenTableService,
        private val cache: OpenTableLocalCache
) {

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): RepoSearchResult {
        Log.d("OpenTableRepository ", "New query: $query")

        // Get data source factory from the local cache
        val dataSourceFactory = cache.restaurantsByCity(query)
        val repoBoundaryCallback = RepoBoundaryCallback(query, service, cache)
        val networkErrors = repoBoundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(repoBoundaryCallback)
                .build()

        return RepoSearchResult(data, networkErrors)
    }

    fun getFavorites(): LiveData<PagedList<Repo>> {
        val dataSourceFactory=cache.getFavorites()

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .build()

        return data

    }

    fun updateRestaurants(id:Int){
        Log.d("OpenTableRepository ","Update  favorites")
        cache.updateRestaurants(id)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 25
    }
}
