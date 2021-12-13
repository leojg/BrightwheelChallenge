package com.sparkdigital.brightwheelchallenge.useCase

import com.sparkdigital.brightwheelchallenge.domain.RepositoryItem
import com.sparkdigital.brightwheelchallenge.repository.GithubRepository
import com.sparkdigital.brightwheelchallenge.domain.Result
import com.sparkdigital.brightwheelchallenge.repository.mapper.RepoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetRepoTopContributorUseCase @Inject constructor(
    val repository: GithubRepository
) {

    suspend operator fun invoke(repoItem: RepositoryItem): Flow<Result<RepositoryItem>> {
        return flow<Result<RepositoryItem>> {
            repository.getRepositoryTopContributor(RepoMapper.mapFromItem(repoItem)).collect { contributor ->
                contributor?.let {
                    repoItem.topContributor = contributor.name
                }
                emit(Result.Success(repoItem))
            }
        }.catch {
            Timber.d(it)
            emit(Result.Failure(it))
        }
    }

}