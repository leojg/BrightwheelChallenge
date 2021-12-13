package com.sparkdigital.brightwheelchallenge.repository.mapper

import com.sparkdigital.brightwheelchallenge.domain.Repository
import com.sparkdigital.brightwheelchallenge.domain.RepositoryItem
import com.sparkdigital.brightwheelchallenge.repository.network.RepositoryResponse

object RepoMapper {
    fun mapFromReponse(from: RepositoryResponse): Repository? =
        from.fullName.split("/").takeIf { it.size == 2 }?.let {
            Repository(it[1], it[0], from.stargazersCount)
        }

    fun mapFromItem(from: RepositoryItem) = Repository(from.name, from.owner, from.stars)
}