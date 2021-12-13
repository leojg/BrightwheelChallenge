package com.sparkdigital.brightwheelchallenge.useCase

import com.sparkdigital.brightwheelchallenge.domain.RepositoryItem
import com.sparkdigital.brightwheelchallenge.repository.GithubRepository
import com.sparkdigital.brightwheelchallenge.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetReposListUseCase @Inject constructor(
    val repository: GithubRepository
) {

    private val pageSize = 10

    suspend operator fun invoke(page: Int = 1): Flow<Result<List<RepositoryItem>>> {
        return flow {
            if (page * pageSize <= 100) {
                repository.getRepositoryList(page, pageSize).collect { list ->
                    val repoItemsList = list?.mapNotNull { repo ->
                        repo?.let {
                            RepositoryItem(repo.name, repo.owner, repo.stars)
                        }
                    } ?: kotlin.run {
                        emptyList()
                    }
                    emit(Result.Success(repoItemsList))
                }
            } else {
                emit(Result.Complete("max number of pages reached"))
            }
        }.catch {
            Timber.d(it)
            emit(Result.Failure(it))
        }
    }

}