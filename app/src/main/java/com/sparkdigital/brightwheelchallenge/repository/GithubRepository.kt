package com.sparkdigital.brightwheelchallenge.repository

import android.util.Log
import com.sparkdigital.brightwheelchallenge.domain.Contributor
import com.sparkdigital.brightwheelchallenge.domain.Repository
import com.sparkdigital.brightwheelchallenge.repository.mapper.ContributorMapper
import com.sparkdigital.brightwheelchallenge.repository.mapper.RepoMapper
import com.sparkdigital.brightwheelchallenge.repository.network.GithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val service: GithubService
) {

    suspend fun getRepositoryList(page: Int, pageSize: Int): Flow<List<Repository?>?> = flow {

        service.getRepositories(page = page, perPage = pageSize).apply {
            if (isSuccessful) {
                val items = body()?.items?.map {
                    RepoMapper.mapFromReponse(it)
                }
                emit(items)
            } else {
                Timber.d(message())
            }
        }
    }

    suspend fun getRepositoryTopContributor(repo: Repository): Flow<Contributor?> = flow {
        service.getRepoTopContributor(repo.owner, repo.name).apply {
            if (isSuccessful) {
                body()?.firstOrNull()?.let {
                    emit(ContributorMapper.mapFromReponse(it))
                }
            } else {
                Timber.d(message())
            }
        }
    }
}