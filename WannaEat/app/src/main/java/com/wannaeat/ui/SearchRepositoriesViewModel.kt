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

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.wannaeat.data.OpenTableRepository
import com.wannaeat.db.OpenTableLocalCache
import com.wannaeat.model.Repo
import com.wannaeat.model.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * The ViewModel works with the [OpenTableRepository ] to get the data.
 */
class SearchRepositoriesViewModel(private val repository: OpenTableRepository ) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }
    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it ->
        it.networkErrors
    }

     val repoResultFavorite: LiveData<PagedList<Repo>> = repository.getFavorites()




    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    /**
     * Get the last query value.
     */

    fun lastQueryValue(): String? = queryLiveData.value

    fun updateRestaurant(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateRestaurants(id)
        }
    }
    fun getFavorites(): LiveData<PagedList<Repo>> {

        return repository.getFavorites()

    }

    companion object{
        public var selectedRepo: Repo? = null
    }
}
