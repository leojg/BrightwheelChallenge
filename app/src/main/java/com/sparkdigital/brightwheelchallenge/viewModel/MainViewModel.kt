package com.sparkdigital.brightwheelchallenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparkdigital.brightwheelchallenge.domain.RepositoryItem
import com.sparkdigital.brightwheelchallenge.domain.Result
import com.sparkdigital.brightwheelchallenge.useCase.GetRepoTopContributorUseCase
import com.sparkdigital.brightwheelchallenge.useCase.GetReposListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getReposListUseCase: GetReposListUseCase,
    private val getRepoTopContributorUseCase: GetRepoTopContributorUseCase,
): ViewModel() {

    private val _itemsListState = MutableStateFlow<List<RepositoryItem>>(emptyList())
    val itemsListState: StateFlow<List<RepositoryItem>> get() = _itemsListState

    private val _repoWithContributorState = MutableStateFlow<RepositoryItem?>(null)
    val repoWithContributor: StateFlow<RepositoryItem?> get() = _repoWithContributorState

    private val _progressState = MutableStateFlow<Boolean>(false)
    val progressState: StateFlow<Boolean> get() = _progressState

    private var isLoading = false
    private var canFetchMore = true

    private var page: Int = 1

    fun getRepos() {
        isLoading = true
        viewModelScope.launch {
            _progressState.value = true
            getReposListUseCase(page).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _itemsListState.value = result.value
                        page += 1
                        for (item in result.value) {
                            getRepoTopContributor(item)
                        }
                    }
                    is Result.Complete -> {
                        canFetchMore = false
                    }
                    is Result.Failure -> {

                    }
                }
                isLoading = false
                _progressState.value = false
            }
        }
    }

    private fun getRepoTopContributor(repositoryItem: RepositoryItem) {
        viewModelScope.launch {
            getRepoTopContributorUseCase(repositoryItem).collect { result ->
                if (result is Result.Success) {
                     _repoWithContributorState.value = result.value
                }
            }
        }
    }

    fun canFetchMore() = canFetchMore && !isLoading

}