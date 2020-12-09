package com.wannaeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.wannaeat.R
import com.wannaeat.model.Repo
import com.wannaeat.ui.ReposAdapter
import com.wannaeat.ui.SearchRepositoriesActivity
import com.wannaeat.ui.SearchRepositoriesViewModel
import kotlinx.android.synthetic.main.activity_search_repositories.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlin.collections.emptyList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        this.supportActionBar?.hide()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.navHostController)
        bottomNavigationView.setupWithNavController(navController)
        // get the view model

    }
}