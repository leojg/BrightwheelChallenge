package com.sparkdigital.brightwheelchallenge.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparkdigital.brightwheelchallenge.R
import com.sparkdigital.brightwheelchallenge.ui.adapter.RepoAdapter
import com.sparkdigital.brightwheelchallenge.utils.PaginationListener
import com.sparkdigital.brightwheelchallenge.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RepoAdapter()
        list.adapter = adapter

        initObservers()
        viewModel.getRepos()

        list.addOnScrollListener(object: PaginationListener(list.layoutManager as LinearLayoutManager) {
            override val canLoad: Boolean get() = viewModel.canFetchMore()
            override fun loadMoreItems() {
                viewModel.getRepos()
            }

        })
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.itemsListState.collect {
                adapter.addItems(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.repoWithContributor.collect {
                adapter.updateItem(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.progressState.collect {
                progress.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

}