package com.wannaeat.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.wannaeat.api.OpenTableService
import com.wannaeat.api.searchRepos
import com.wannaeat.db.OpenTableLocalCache
import com.wannaeat.model.Repo

class RepoBoundaryCallback(
        private val query: String,
        private val service: OpenTableService,
        private val cache: OpenTableLocalCache
) : PagedList.BoundaryCallback<Repo>() {

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos(service, query,NETWORK_PAGE_SIZE,lastRequestedPage, { repos ->
            cache.insert(repos) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }


    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
        requestAndSaveData(query)
    }
}